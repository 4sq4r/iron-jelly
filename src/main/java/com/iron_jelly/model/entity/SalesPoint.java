package com.iron_jelly.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

@Data
@Entity
@Table(name = "sales_points")
@EqualsAndHashCode(callSuper = true)
public class SalesPoint extends Base {

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @OneToMany(mappedBy = "salesPoint")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<CardTemplate> cardTemplates;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}
