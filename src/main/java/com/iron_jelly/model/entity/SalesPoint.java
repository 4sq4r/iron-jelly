package com.iron_jelly.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@Entity
@Table(name = "sales_points")
@EqualsAndHashCode(callSuper = true)
public class SalesPoint extends Base {

    private String name;

    @OneToMany(mappedBy = "salesPoint")
    private Set<CardTemplate> cardTemplateSet;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}
