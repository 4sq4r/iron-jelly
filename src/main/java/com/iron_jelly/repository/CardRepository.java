package com.iron_jelly.repository;

import com.iron_jelly.model.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Card> findByExternalId(UUID externalId);
    List<Card> findCardsByUserExternalIdAndActiveIsTrue(UUID externalId);
    List<Card> findAllByExpireDateBeforeAndActive(LocalDate date, boolean active);
}
