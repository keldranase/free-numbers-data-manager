package dev.alex.klepov.model.converters;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import dev.alex.klepov.client.view.FreeCountryClientView;
import dev.alex.klepov.client.view.FreeNumberClientView;
import dev.alex.klepov.model.FreeCountryModel;
import dev.alex.klepov.model.FreeNumberModel;

public class ViewModelEntityConverter {

    private static final DateTimeFormatter ONLINESIM_FILE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static FreeCountryModel countryToModel(FreeCountryClientView freeCountryClientView) {
        return new FreeCountryModel(freeCountryClientView.getCountry(), freeCountryClientView.getCountryName());
    }

//    public static FreeCountryEntity countryToEntity(FreeCountryModel model) {
//        return new FreeCountryEntity()
//                .setCode(model.getCountryCode())
//                .setName(model.getCountryName());
//    }

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

//    public static FreeNumberEntity numberModelToEntity(FreeNumberModel model) {
//        return new FreeNumberEntity()
//                .setNumber(model.getNumber())
//                .setCountryCode(null)
//                .setUpdatedAt(Timestamp.valueOf(model.getUpdatedAt()))
//                .setDataHumans(model.getDataHumans())
//                .setFullNumber(model.getFullNumber())
//                .setCountryText(model.getCountryText())
//                .setMaxdate(Timestamp.valueOf(model.getMaxdate()))
//                .setStatus(model.getStatus());
//    }

//    public static FreeNumberModel numberEntityToModel(FreeNumberEntity entity) {
//        return new FreeNumberModel(entity.getNumber(),
//                null,
//                entity.getUpdatedAt().toLocalDateTime(),
//                entity.getDataHumans(),
//                entity.getFullNumber(),
//                entity.getCountryText(),
//                entity.getMaxdate().toLocalDateTime(),
//                entity.getStatus());
//    }
}
