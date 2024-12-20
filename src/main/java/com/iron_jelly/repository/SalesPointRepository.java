package com.iron_jelly.repository;

import com.iron_jelly.model.entity.Company;
import com.iron_jelly.model.entity.SalesPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SalesPointRepository extends JpaRepository<SalesPoint, Long> {
    Optional<SalesPoint> findByExternalId(UUID externalId);
}
