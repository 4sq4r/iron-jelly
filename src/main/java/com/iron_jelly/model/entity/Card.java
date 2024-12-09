package com.iron_jelly.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "cards")
@EqualsAndHashCode(callSuper = true)
public class Card extends Base {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(name = "isActive", nullable = false)
    boolean isActive = true;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "card_template_id", nullable = false)
    private CardTemplate cardTemplate;
    @Min(value = 1)
    @Column(name = "usage_limit", nullable = false)
    private Integer usageLimit;
}
