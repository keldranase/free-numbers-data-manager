package dev.alex.klepov.controller.view;

import java.util.List;
import java.util.Map;

public class FreeNumbersByCountryResponseView {

    private final Map<Long, List<FreeNumberResponseView>> numbersByCountry;

    public FreeNumbersByCountryResponseView(Map<Long, List<FreeNumberResponseView>> numbersByCountry) {
        this.numbersByCountry = numbersByCountry;
    }

    public Map<Long, List<FreeNumberResponseView>> getNumbersByCountry() {
        return numbersByCountry;
    }
}
