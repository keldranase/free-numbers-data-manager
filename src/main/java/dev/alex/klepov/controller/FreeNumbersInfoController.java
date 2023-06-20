package dev.alex.klepov.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import dev.alex.klepov.controller.view.FreeNumberResponseView;
import dev.alex.klepov.controller.view.FreeNumbersByCountryResponseView;
import dev.alex.klepov.model.converters.ViewModelConverter;
import dev.alex.klepov.service.FreeNumberService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/freeNumbers") // in case we'd want to move to the new version of api
public class FreeNumbersInfoController {

    private static final Logger LOGGER = LogManager.getLogger(FreeNumbersInfoController.class);

    private final FreeNumberService freeNumberService;

    public FreeNumbersInfoController(FreeNumberService freeNumberService) {
        this.freeNumberService = freeNumberService;
    }

    @PostMapping("/byCountry")
    public ResponseEntity<FreeNumbersByCountryResponseView> getFreeNumbersByCountry(
            @RequestParam(required = false, defaultValue = "true") boolean doUpdateDb,
            @RequestParam(required = false, defaultValue = "") Set<Long> countryCodes

    ) {
        LOGGER.debug("Starting to serve request for endpoint: /byCountry");

        FreeNumbersParamsValidator.validateCountryCodes(countryCodes);

        new TreeMap<>();

        Map<Long, List<FreeNumberResponseView>> response = freeNumberService
                .getFreeNumbersAndUpdateDb(doUpdateDb, countryCodes).entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().stream().map(ViewModelConverter::modelNumberToResponseView).toList(),
                        (a, b) -> a,
                        TreeMap::new));

        LOGGER.debug("Fequest for endpoint: /byCountry finished, returning response");
        return ResponseEntity.ok(new FreeNumbersByCountryResponseView(response));
    }
}
