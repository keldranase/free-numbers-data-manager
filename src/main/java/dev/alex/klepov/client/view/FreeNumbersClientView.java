package dev.alex.klepov.client.view;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class FreeNumbersClientView {

    @SerializedName("response")
    private int statusCode;

    @SerializedName("numbers")
    private List<FreeNumberClientView> numbers;

    public int getStatusCode() {
        return statusCode;
    }

    public List<FreeNumberClientView> getNumbers() {
        return numbers;
    }
}
