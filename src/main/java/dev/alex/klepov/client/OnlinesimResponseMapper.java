package dev.alex.klepov.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.alex.klepov.client.view.FreeCountryListClientView;
import dev.alex.klepov.client.view.FreeNumbersClientView;

public class OnlinesimResponseMapper {

    private static final Gson gson = new GsonBuilder().create();

    public static FreeCountryListClientView mapFreeCountries(String jsonResponseBody) {
        return gson.fromJson(jsonResponseBody, FreeCountryListClientView.class);
    }

    public static FreeNumbersClientView mapFreeNumbers(String json) {
        return gson.fromJson(json, FreeNumbersClientView.class);
    }
}
