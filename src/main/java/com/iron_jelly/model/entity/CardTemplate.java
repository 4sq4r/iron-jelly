package com.iron_jelly.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "card_templates")
@EqualsAndHashCode(callSuper = true)
public class CardTemplate extends Base {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;
    @Min(value = 1)
    @Column(name = "usage_limit", nullable = false)
    private Integer usageLimit;
    @Column(name = "expire_days")
    private Integer expireDays;
    @Column(name = "description", length = 500)
    private String description;
}
