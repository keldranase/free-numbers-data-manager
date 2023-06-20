package dev.alex.klepov.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class FreeNumberModel {

    private Long id;

    private String number;

    private Long countryCode;

    private LocalDateTime updatedAt;

    private String dataHumans;

    private String fullNumber;

    private String countryText;

    private LocalDateTime maxdate;

    private String status;

    public FreeNumberModel(Long id, String number, Long countryCode, LocalDateTime updatedAt, String dataHumans,
                           String fullNumber, String countryText, LocalDateTime maxdate, String status) {
        this.id = id;
        this.number = number;
        this.countryCode = countryCode;
        this.updatedAt = updatedAt;
        this.dataHumans = dataHumans;
        this.fullNumber = fullNumber;
        this.countryText = countryText;
        this.maxdate = maxdate;
        this.status = status;
    }

    public FreeNumberModel(String number, Long countryCode, LocalDateTime updatedAt, String dataHumans,
                           String fullNumber, String countryText, LocalDateTime maxdate, String status) {
        this.id = null;
        this.number = number;
        this.countryCode = countryCode;
        this.updatedAt = updatedAt;
        this.dataHumans = dataHumans;
        this.fullNumber = fullNumber;
        this.countryText = countryText;
        this.maxdate = maxdate;
        this.status = status;
    }



    public Long getId() {
        return id;
    }

    public FreeNumberModel setId(Long id) {
        this.id = id;
        return this;
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


    // Comparing numbers only on full-number basis
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FreeNumberModel that = (FreeNumberModel) o;
        return fullNumber.equals(that.fullNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullNumber);
    }
}
