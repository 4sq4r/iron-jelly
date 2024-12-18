package com.iron_jelly.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "sales_points")
@EqualsAndHashCode(callSuper = true)
public class SalesPoint extends Base {

    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}
