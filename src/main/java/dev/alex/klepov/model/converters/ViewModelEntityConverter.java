package dev.alex.klepov.model.converters;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import dev.alex.klepov.client.view.FreeNumberClientView;
import dev.alex.klepov.model.FreeNumberModel;

public class ViewModelEntityConverter {

    private static final DateTimeFormatter ONLINESIM_FILE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static FreeNumberModel numberToModel(FreeNumberClientView model) {
        return new FreeNumberModel(
                0L,
                model.getNumber(),
                Long.valueOf(model.getCountryCode()),
                LocalDateTime.parse(model.getUpdatedAt(), ONLINESIM_FILE_FORMAT),
                model.getDataHumans(),
                model.getFullNumber(),
                model.getCountryText(),
                LocalDateTime.parse(model.getMaxdate(), ONLINESIM_FILE_FORMAT),
                model.getStatus());
    }
}
