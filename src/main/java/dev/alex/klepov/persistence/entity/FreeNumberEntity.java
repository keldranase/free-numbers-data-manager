package dev.alex.klepov.persistence.entity;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "free_number"/*, indexes = {
        @Index(name = "FREE_COUNTRY_CODE_INDEX_UNIQUE", columnList = "code", unique = true),
        @Index(name = "FREE_COUNTRY_NAME_INDEX_NON_UNIQUE", columnList = "fullNumber", unique = true)}*/)
public class FreeNumberEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FREE_NUMBERS_ID_SEQ")
    private Long id;

    private String number;

    private FreeCountryEntity countryCode;

    private Timestamp updatedAt;

    private String dataHumans;

    private String fullNumber;

    private String countryText;

    private Timestamp maxdate;

    private String status;

    public String getNumber() {
        return number;
    }

    public FreeNumberEntity setNumber(String number) {
        this.number = number;
        return this;
    }

    public FreeCountryEntity getCountryCode() {
        return countryCode;
    }

    public FreeNumberEntity setCountryCode(FreeCountryEntity countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public FreeNumberEntity setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public String getDataHumans() {
        return dataHumans;
    }

    public FreeNumberEntity setDataHumans(String dataHumans) {
        this.dataHumans = dataHumans;
        return this;
    }

    public String getFullNumber() {
        return fullNumber;
    }

    public FreeNumberEntity setFullNumber(String fullNumber) {
        this.fullNumber = fullNumber;
        return this;
    }

    public String getCountryText() {
        return countryText;
    }

    public FreeNumberEntity setCountryText(String countryText) {
        this.countryText = countryText;
        return this;
    }

    public Timestamp getMaxdate() {
        return maxdate;
    }

    public FreeNumberEntity setMaxdate(Timestamp maxdate) {
        this.maxdate = maxdate;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public FreeNumberEntity setStatus(String status) {
        this.status = status;
        return this;
    }

    public Long getId() {
        return id;
    }

    public FreeNumberEntity setId(Long id) {
        this.id = id;

        int[] ints = new int[5];


        return this;
    }
}
