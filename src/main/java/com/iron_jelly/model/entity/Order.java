package com.iron_jelly.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "orders")
@EqualsAndHashCode(callSuper = true)
public class Order extends Base {

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;
    @Column(name = "is_free", nullable = false)
    private Boolean isFree;
}
