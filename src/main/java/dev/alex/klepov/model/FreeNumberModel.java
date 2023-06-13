package dev.alex.klepov.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class FreeNumberModel {

    private String number;

    private Long countryCode;

    private LocalDateTime updatedAt;

    private String dataHumans;

    private String fullNumber;

    private String countryText;

    private LocalDateTime maxdate;

    private String status;

    public FreeNumberModel(String number, Long countryCode, LocalDateTime updatedAt, String dataHumans,
                           String fullNumber, String countryText, LocalDateTime maxdate, String status) {
        this.number = number;
        this.countryCode = countryCode;
        this.updatedAt = updatedAt;
        this.dataHumans = dataHumans;
        this.fullNumber = fullNumber;
        this.countryText = countryText;
        this.maxdate = maxdate;
        this.status = status;

//        String dateString = "2012-11-13 14:00:00:000";
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d H:m:s:S");
//        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
//        Date theDate = sdf.parse(dateString);
//
//        new SimpleDateFormat("yyyy-M-d H:m:s:S");
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FreeNumberModel that = (FreeNumberModel) o;
        return number.equals(that.number) && countryCode.equals(that.countryCode) && updatedAt.equals(that.updatedAt) && dataHumans.equals(that.dataHumans) && fullNumber.equals(that.fullNumber) && countryText.equals(that.countryText) && maxdate.equals(that.maxdate) && status.equals(that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, countryCode, updatedAt, dataHumans, fullNumber, countryText, maxdate, status);
    }
}
