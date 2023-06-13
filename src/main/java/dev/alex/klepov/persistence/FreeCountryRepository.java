package dev.alex.klepov.persistence;

import dev.alex.klepov.persistence.entity.FreeCountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreeCountryRepository extends JpaRepository<FreeCountryEntity, Long> {

}
