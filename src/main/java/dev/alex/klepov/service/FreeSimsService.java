package dev.alex.klepov.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import dev.alex.klepov.client.OnlinesimApiClient;
import dev.alex.klepov.client.view.FreeCountryClientView;
import dev.alex.klepov.model.FreeNumberModel;
import dev.alex.klepov.model.converters.ViewModelEntityConverter;
import dev.alex.klepov.persistence.FreeNumberDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

public class FreeSimsService {

    private static final Logger LOGGER = LogManager.getLogger(FreeSimsService.class);

    private final OnlinesimApiClient onlinesimApiClient;
    private final FreeNumberDao freeNumberDao;

    public FreeSimsService(OnlinesimApiClient onlinesimApiClient, FreeNumberDao freeNumberDao) {
        this.onlinesimApiClient = onlinesimApiClient;
        this.freeNumberDao = freeNumberDao;
    }

    @Transactional
    public Map<Long, List<FreeNumberModel>> getFreeNumbersAndUpdateDb(boolean doUpdateDb, Set<Long> countryCodes) {
        Map<Long, List<FreeNumberModel>> numbersByCountry = freeNumbersByCountry(countryCodes);

        // db lookup backup
        if (doUpdateDb) {
            freeNumbersByCountryUpdateToDb(numbersByCountry);
        }

        return numbersByCountry;
    }

    public Map<Long, List<FreeNumberModel>> freeNumbersByCountry(Set<Long> countryCodes) {
        LOGGER.debug("Fetching numbers by country for countries: " + countryCodes);

        List<FreeCountryClientView> freeCountryList = onlinesimApiClient.getFreeCountryList()
                .stream()
                .filter(c -> countryCodes.isEmpty() || countryCodes.contains(c.getCountry()))
                .toList();

        return freeCountryList.stream()
                .map(FreeCountryClientView::getCountry)
                .map(onlinesimApiClient::getFreePhoneList)
                .flatMap(Collection::stream)
                .map(ViewModelEntityConverter::numberToModel)
                .collect(Collectors.groupingBy(
                        FreeNumberModel::getCountryCode,
                        TreeMap::new, // to order by countryCode
                        Collectors.toList()));
    }

    public void freeNumbersByCountryUpdateToDb(Map<Long, List<FreeNumberModel>> numbersByCountry) {
        Set<FreeNumberModel> fromClient = numbersByCountry.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        Set<FreeNumberModel> fromDB = new HashSet<>(freeNumberDao.findAll());

        Set<FreeNumberModel> toAdd = new HashSet<>(fromClient);
        toAdd.removeAll(fromDB);

        Set<FreeNumberModel> toDelete = new HashSet<>(fromDB);
        toAdd.removeAll(fromClient);

        LOGGER.debug("Following numbers going to be saved to DB: " + toAdd);
        LOGGER.debug("Following numbers going to be deleted from DB: " + toDelete);

        freeNumberDao.saveBatch(toAdd);
        freeNumberDao.deleteBatch(toDelete.stream().map(FreeNumberModel::getId).toList());
    }
}
