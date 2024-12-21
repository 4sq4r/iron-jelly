package com.iron_jelly.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "card_templates")
@EqualsAndHashCode(callSuper = true)
public class CardTemplate extends Base {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sales_point_id", nullable = false)
    private  SalesPoint salesPoint;
    @Column(name = "title", length = 50)
    private String title;
    @Column(name = "limit_value")
    private Integer limitValue;
    @Column(name = "expire_days")
    private Integer expireDays;
    @Column(name = "description", length = 500)
    private String description;
    @Column(name = "is_active")
    private Boolean active;
}
