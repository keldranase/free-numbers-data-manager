package dev.alex.klepov.client.view;

import com.google.gson.annotations.SerializedName;

public class FreeCountryClientView {

    @SerializedName("country")
    private int country;

    @SerializedName("country_text")
    private String countryName;

    public int getCountry() {
        return country;
    }

    public String getCountryName() {
        return countryName;
    }
}
