package dev.alex.klepov.controller.view;

public class FreeNumberResponseView {

    private String number;

    private int countryCode;

    private String updatedAt; // date

    private String dataHumans;

    private String fullNumber;

    private String countryText;

    private String maxdate;

    private String status;

    public FreeNumberResponseView(String number, int countryCode, String updatedAt, String dataHumans,
                                  String fullNumber, String countryText, String maxdate, String status) {
        this.number = number;
        this.countryCode = countryCode;
        this.updatedAt = updatedAt;
        this.dataHumans = dataHumans;
        this.fullNumber = fullNumber;
        this.countryText = countryText;
        this.maxdate = maxdate;
        this.status = status;
    }
}
