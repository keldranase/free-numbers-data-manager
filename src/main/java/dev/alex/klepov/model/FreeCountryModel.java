package dev.alex.klepov.model;

public class FreeCountryModel {

    private final long countryCode;
    private final String countryName;

    public FreeCountryModel(long countryCode, String countryName) {
        this.countryCode = countryCode;
        this.countryName = countryName;
    }

    public long getCountryCode() {
        return countryCode;
    }

    public String getCountryName() {
        return countryName;
    }
}
