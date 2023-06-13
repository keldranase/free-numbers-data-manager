package dev.alex.klepov.client.view;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class FreeCountryListClientView {

    @SerializedName("response")
    private int statusCode;

    @SerializedName("countries")
    private List<FreeCountryClientView> countries;

    public int getStatusCode() {
        return statusCode;
    }

    public List<FreeCountryClientView> getCountries() {
        return countries;
    }
}
