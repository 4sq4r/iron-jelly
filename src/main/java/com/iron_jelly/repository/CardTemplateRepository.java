package com.iron_jelly.repository;

import com.iron_jelly.model.entity.CardTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardTemplateRepository extends JpaRepository<CardTemplate, Long>  {
    Optional<CardTemplate> findByExternalId(UUID externalId);
}
