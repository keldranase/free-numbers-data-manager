package dev.alex.klepov.model.converters;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import dev.alex.klepov.client.view.FreeNumberClientView;
import dev.alex.klepov.controller.view.FreeNumberResponseView;
import dev.alex.klepov.model.FreeNumberModel;

public class ViewModelConverter {

    private static final DateTimeFormatter ONLINESIM_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static FreeNumberModel clientViewNumberToModel(FreeNumberClientView model) {
        return new FreeNumberModel(
                model.getNumber(),
                model.getCountryCode(),
                LocalDateTime.parse(model.getUpdatedAt(), ONLINESIM_DATE_FORMAT),
                model.getDataHumans(),
                model.getFullNumber(),
                model.getCountryText(),
                LocalDateTime.parse(model.getMaxdate(), ONLINESIM_DATE_FORMAT),
                model.getStatus());
    }

    public static FreeNumberResponseView modelNumberToResponseView(FreeNumberModel model) {
        return new FreeNumberResponseView(
                model.getNumber(),
                model.getCountryCode(),
                model.getUpdatedAt(),
                model.getDataHumans(),
                model.getFullNumber(),
                model.getCountryText(),
                model.getMaxdate(),
                model.getStatus()
        );
    }
}
