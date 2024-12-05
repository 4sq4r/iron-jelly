package com.iron_jelly.repository;

import com.iron_jelly.model.entity.CardTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardTemplateRepository extends JpaRepository<CardTemplate, Long>  {

}
