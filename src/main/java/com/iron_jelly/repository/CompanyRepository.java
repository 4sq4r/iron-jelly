package com.iron_jelly.repository;

import com.iron_jelly.model.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByExternalId(UUID externalId);
}
