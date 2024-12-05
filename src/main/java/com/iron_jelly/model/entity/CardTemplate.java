package com.iron_jelly.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "card_templates")
@EqualsAndHashCode(callSuper = true)
public class CardTemplate extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_template_id", nullable = false)
    private long cardTemplateId;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_id", referencedColumnName = "company_id", nullable = false)
    private Company company;
    @Column(name = "company_name", nullable = false)
    private String name;
    @Column(name = "limit")
    private int limit;
    @Column(name = "expire_days")
    private int expireDays;
    @Column(name = "description")
    private String description;
}
