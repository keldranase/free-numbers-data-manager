package dev.alex.klepov.client.view;

import com.google.gson.annotations.SerializedName;

public class FreeNumberClientView {

    @SerializedName("number")
    private String number;

    @SerializedName("country")
    private int countryCode;

    @SerializedName("updated_at")
    private String updatedAt; // date

    @SerializedName("data_humans")
    private String dataHumans;

    @SerializedName("full_number")
    private String fullNumber;

    @SerializedName("country_text")
    private String countryText;

    @SerializedName("maxdate") // date
    private String maxdate;

    @SerializedName("status")
    private String status;

    public String getNumber() {
        return number;
    }

    public int getCountryCode() {
        return countryCode;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getDataHumans() {
        return dataHumans;
    }

    public String getFullNumber() {
        return fullNumber;
    }

    public String getCountryText() {
        return countryText;
    }

    public String getMaxdate() {
        return maxdate;
    }

    public String getStatus() {
        return status;
    }
}
