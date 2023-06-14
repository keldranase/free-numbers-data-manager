package dev.alex.klepov.controller;

import java.util.Collection;
import java.util.List;

import dev.alex.klepov.controller.exception.BadParamException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FreeNumbersParamsValidator {

    private static final Logger LOGGER = LogManager.getLogger(FreeNumbersParamsValidator.class);

    public static void validateCountryCodes(Collection<Long> countryCodes) {
        List<Long> badCodes = countryCodes.stream()
                .filter(code -> code < 0)
                .toList();

        if (badCodes.isEmpty()) {
            LOGGER.debug("Validation of country codes completed successfully");
            return;
        }

        String errorMessage = "Country codes must be greater than 0, but were found: " + badCodes;
        LOGGER.error(errorMessage);
        throw new BadParamException(errorMessage);
    }
}
