package com.iron_jelly.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.UUID;

@Data
@Entity
@Table(name = "companies")
@EqualsAndHashCode(callSuper = true)
public class Company extends Base{

    @Id
    @Column(name = "company_id", nullable = false)
    private UUID companyId;
    @Column(name = "company_name", nullable = false)
    private String name;
}
