package com.iron_jelly.model.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "cards")
public class Card extends Base {

    @Column(name = "user_id", nullable = false)
    private UUID userId;
    @Column(name = "company_id", nullable = false)
    private UUID companyId;
}
