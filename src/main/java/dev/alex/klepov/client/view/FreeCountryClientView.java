package dev.alex.klepov.client.view;

import com.google.gson.annotations.SerializedName;

public class FreeCountryClientView {

    @SerializedName("country")
    private long country;

    @SerializedName("country_text")
    private String countryName;

    public long getCountry() {
        return country;
    }

    public String getCountryName() {
        return countryName;
    }
}
