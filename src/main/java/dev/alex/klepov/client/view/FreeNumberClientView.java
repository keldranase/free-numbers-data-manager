package dev.alex.klepov.client.view;

import com.google.gson.annotations.SerializedName;

public class FreeNumberClientView {

    // technically could be represented by a numeric type, but I don't see a point in it
    @SerializedName("number")
    private String number;

    @SerializedName("country")
    private Long countryCode;

    // IMHO, it's better to accept data in a form of a string, and later to convert to date or anything else
    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("data_humans")
    private String dataHumans;

    @SerializedName("full_number")
    private String fullNumber;

    @SerializedName("country_text")
    private String countryText;

    @SerializedName("maxdate")
    private String maxdate;

    @SerializedName("status")
    private String status;

    public String getNumber() {
        return number;
    }

    public Long getCountryCode() {
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
