package dev.alex.klepov.persistence.entity;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

@Entity
@Table(name = "FREE_COUNTRY", indexes = {
        @Index(name = "FREE_COUNTRY_CODE_INDEX_UNIQUE", columnList = "code", unique = true),
        @Index(name = "FREE_COUNTRY_NAME_INDEX_NON_UNIQUE", columnList = "name", unique = false)})
public class FreeCountryEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FREE_COUNTRY_ID_SEQ")
    private Long id;

    private int code;

    private String name;

    private boolean isDeleted;

    private Timestamp timeCreated;

    private Timestamp timeUpdated;

    public Long getId() {
        return id;
    }

    public int getCode() {
        return code;
    }

    public FreeCountryEntity setCode(int code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public FreeCountryEntity setName(String name) {
        this.name = name;
        return this;
    }
}
