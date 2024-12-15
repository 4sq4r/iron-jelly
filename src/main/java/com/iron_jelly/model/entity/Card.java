package com.iron_jelly.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import java.util.Set;

@Data
@Entity
@Table(name = "cards")
@EqualsAndHashCode(callSuper = true)
public class Card extends Base {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(name = "isActive", nullable = false)
    boolean isActive;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "card_template_id", nullable = false)
    private CardTemplate cardTemplate;
    @OneToMany(mappedBy = "card")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Order> orders;
    @Column(name = "count_orders")
    private Integer countOrders;
}
