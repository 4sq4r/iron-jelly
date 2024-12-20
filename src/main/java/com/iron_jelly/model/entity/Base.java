package com.iron_jelly.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@MappedSuperclass
public class Base implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "external_id", nullable = false)
    private UUID externalId;
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    @Column(name = "created_by", nullable = false)
    private String createdBy;
    @Column(name = "updated_by", nullable = false)
    private String updatedBy;

    @PrePersist
    void prePersist() {
        LocalDateTime n = LocalDateTime.now();

        if (this.createdAt == null) {
            this.createdAt = n;
        }

        if (this.updatedAt == null) {
            this.updatedAt = n;
        }

        if (this.externalId == null) {
            this.externalId = UUID.randomUUID();
        }
    }

    @PreUpdate
    void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
