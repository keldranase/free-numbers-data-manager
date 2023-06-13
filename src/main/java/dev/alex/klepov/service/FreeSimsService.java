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
import dev.alex.klepov.persistence.FreeCountryRepository;
import dev.alex.klepov.persistence.FreeNumberRepository;
import org.springframework.transaction.annotation.Transactional;

public class FreeSimsService {

    private final OnlinesimApiClient onlinesimApiClient;

    private final FreeCountryRepository freeCountryRepository;
    private final FreeNumberRepository freeNumberRepository;

    public FreeSimsService(OnlinesimApiClient onlinesimApiClient,
                           FreeCountryRepository freeCountryRepository,
                           FreeNumberRepository freeNumberRepository) {
        this.onlinesimApiClient = onlinesimApiClient;
        this.freeCountryRepository = freeCountryRepository;
        this.freeNumberRepository = freeNumberRepository;
    }

    @Transactional
    public Map<Long, List<FreeNumberModel>> getFreeNumbersAndUpdateDb(Set<Long> countryIds) {
        Map<Long, List<FreeNumberModel>> numbersByCountry = freeNumbersByCountry(countryIds);
        freeNumbersByCountryUpdateToDb(numbersByCountry);

        return numbersByCountry;
    }

    public Map<Long, List<FreeNumberModel>> freeNumbersByCountry(Set<Long> countryIds) {
        List<FreeCountryClientView> freeCountryList = onlinesimApiClient.getFreeCountryList()
                .stream()
                .filter(country -> !countryIds.isEmpty() && countryIds.contains(country.getCountry()))
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

        Set<FreeNumberModel> fromDB = freeNumberRepository.findAll().stream()
                .map(ViewModelEntityConverter::numberEntityToModel)
                .collect(Collectors.toSet());

        Set<FreeNumberModel> toAdd = new HashSet<>(fromClient);
        toAdd.removeAll(fromDB);

        Set<FreeNumberModel> toDelete = new HashSet<>(fromDB);
        toAdd.removeAll(fromClient);

        freeNumberRepository.saveAll(toAdd.stream()
                .map(ViewModelEntityConverter::numberModelToEntity)
                .toList());

        freeNumberRepository.deleteAll(toDelete.stream()
                .map(ViewModelEntityConverter::numberModelToEntity)
                .toList());
    }
}
