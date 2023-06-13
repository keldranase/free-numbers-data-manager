package dev.alex.klepov.persistence;

import dev.alex.klepov.persistence.entity.FreeNumberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreeNumberRepository extends JpaRepository<FreeNumberEntity, Long> {

}
