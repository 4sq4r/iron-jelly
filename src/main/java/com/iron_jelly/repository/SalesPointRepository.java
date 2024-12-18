package com.iron_jelly.repository;

import com.iron_jelly.model.entity.SalesPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesPointRepository extends JpaRepository<SalesPoint, Long> {
}
