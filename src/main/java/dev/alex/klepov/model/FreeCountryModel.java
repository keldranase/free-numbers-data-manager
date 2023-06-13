package dev.alex.klepov.model;

public class FreeCountryModel {

    private final int countryCode;
    private final String countryName;

    public FreeCountryModel(int countryCode, String countryName) {
        this.countryCode = countryCode;
        this.countryName = countryName;
    }

    public int getCountryCode() {
        return countryCode;
    }

    public String getCountryName() {
        return countryName;
    }
}
