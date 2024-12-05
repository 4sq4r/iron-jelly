package com.iron_jelly.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "cards")
@EqualsAndHashCode(callSuper = true)
public class Card extends Base {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;
    @Column(name = "isActive", nullable = false)
    boolean isActive = true;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "card_template_id", referencedColumnName = "card_template_id", nullable = false)
    private CardTemplate cardTemplate;
    @JoinColumn(name = "limit", referencedColumnName = "limit")
    private int limit;
}
