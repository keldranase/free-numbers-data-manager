package dev.alex.klepov.controller.view;

import java.util.List;
import java.util.Map;

import dev.alex.klepov.model.FreeNumberModel;

public class FreeNumbersByCountryResponseView {

    private final Map<Long, List<FreeNumberModel>> numbersByCountry;

    public FreeNumbersByCountryResponseView(Map<Long, List<FreeNumberModel>> numbersByCountry) {
        this.numbersByCountry = numbersByCountry;
    }

    public Map<Long, List<FreeNumberModel>> getNumbersByCountry() {
        return numbersByCountry;
    }
}
