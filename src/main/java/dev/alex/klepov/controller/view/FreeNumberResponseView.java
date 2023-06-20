package dev.alex.klepov.controller.view;

import java.time.LocalDateTime;

public class FreeNumberResponseView {

    private String number;

    private Long countryCode;

    private LocalDateTime updatedAt;

    private String dataHumans;

    private String fullNumber;

    private String countryText;

    private LocalDateTime maxdate;

    private String status;

    public FreeNumberResponseView(String number, Long countryCode, LocalDateTime updatedAt, String dataHumans,
                           String fullNumber, String countryText, LocalDateTime maxdate, String status) {
        this.number = number;
        this.countryCode = countryCode;
        this.updatedAt = updatedAt;
        this.dataHumans = dataHumans;
        this.fullNumber = fullNumber;
        this.countryText = countryText;
        this.maxdate = maxdate;
        this.status = status;
    }

    public String getNumber() {
        return number;
    }

    public Long getCountryCode() {
        return countryCode;
    }

    public LocalDateTime getUpdatedAt() {
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

    public LocalDateTime getMaxdate() {
        return maxdate;
    }

    public String getStatus() {
        return status;
    }
}
