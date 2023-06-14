package dev.alex.klepov.service;

import java.lang.management.ManagementFactory;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

import dev.alex.klepov.client.OnlinesimApiClient;
import dev.alex.klepov.client.view.FreeCountryClientView;
import dev.alex.klepov.model.FreeNumberModel;
import dev.alex.klepov.model.converters.ViewModelEntityConverter;
import dev.alex.klepov.persistence.FreeNumberDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

public class FreeNumberService {

    private static final Logger LOGGER = LogManager.getLogger(FreeNumberService.class);


    private final OnlinesimApiClient onlinesimApiClient;
    private final FreeNumberDao freeNumberDao;
    private final ForkJoinPool forkJoinPool;

    public FreeNumberService(OnlinesimApiClient onlinesimApiClient, FreeNumberDao freeNumberDao) {
        this.onlinesimApiClient = onlinesimApiClient;
        this.freeNumberDao = freeNumberDao;

        int threadsToUse = Math.max(ManagementFactory.getThreadMXBean().getThreadCount() - 1, 1);
        forkJoinPool = new ForkJoinPool(threadsToUse);
    }

    public Map<Long, List<FreeNumberModel>> getFreeNumbersAndUpdateDb(boolean doUpdateDb, Set<Long> countryCodes) {
        try {
            Map<Long, List<FreeNumberModel>> numbersByCountry = freeNumbersByCountry(countryCodes);

            if (doUpdateDb) {
                freeNumbersByCountryUpdateToDb(numbersByCountry);
            }

            return numbersByCountry;

        } catch (Exception e) {
            LOGGER.error("Couldn't get response from api, falling back to getting info from db" + e.getMessage());

            return new HashSet<>(freeNumberDao.findAll())
                    .stream()
                    .filter(num -> countryCodes.isEmpty() || countryCodes.contains(num.getCountryCode()))
                    .collect(Collectors.groupingBy(
                            FreeNumberModel::getCountryCode,
                            TreeMap::new, // to order by countryCode
                            Collectors.toList()));
        }
    }

    private Map<Long, List<FreeNumberModel>> freeNumbersByCountry(Set<Long> countryCodes) {
        LOGGER.debug("Fetching numbers by country for countries: " + countryCodes);

        List<FreeCountryClientView> freeCountryList = onlinesimApiClient.getFreeCountryList()
                .stream()
                .filter(c -> countryCodes.isEmpty() || countryCodes.contains(c.getCountry()))
                .toList();

        try {
            return findFreeCountriesConcurrent(freeCountryList);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Someone messed up with concurrency");
        }
    }

    private Map<Long, List<FreeNumberModel>> findFreeCountriesConcurrent(List<FreeCountryClientView> freeCountryList)
            throws InterruptedException, ExecutionException {

        return forkJoinPool.submit(() -> freeCountryList.parallelStream()
                        .map(FreeCountryClientView::getCountry)
                        .map(onlinesimApiClient::getFreePhoneList)
                        .flatMap(Collection::stream)
                        .map(ViewModelEntityConverter::numberToModel)
                        .peek(a -> LOGGER.error("Hello " + Thread.currentThread()))
                        .collect(Collectors.groupingBy(
                                FreeNumberModel::getCountryCode,
                                TreeMap::new, // to order by countryCode
                                Collectors.toList())))
                .get();
    }

    @Transactional(
            // in case we'd want to have concurrent access to DB, weaker levels of isolation won't cut it
            isolation = Isolation.REPEATABLE_READ)
    @Retryable(
            retryFor = Exception.class,
            maxAttemptsExpression = "${onlinesim.data.manager.db.max.retry}",
            backoff = @Backoff(
                    delay = 500,
                    multiplier = 2))
    protected void freeNumbersByCountryUpdateToDb(Map<Long, List<FreeNumberModel>> numbersByCountry) {
        Set<FreeNumberModel> fromClient = numbersByCountry.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        Set<FreeNumberModel> fromDB = new HashSet<>(freeNumberDao.findAll());

        Set<FreeNumberModel> toAdd = new HashSet<>(fromClient);
        toAdd.removeAll(fromDB);

        Set<FreeNumberModel> toDelete = new HashSet<>(fromDB);
        toDelete.removeAll(fromClient);

        LOGGER.debug("Following numbers going to be saved to DB: " + toAdd);
        LOGGER.debug("Following numbers going to be deleted from DB: " + toDelete);

        freeNumberDao.saveBatch(toAdd);
        freeNumberDao.deleteBatch(toDelete.stream().map(FreeNumberModel::getId).toList());
    }
}
