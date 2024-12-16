package com.iron_jelly.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.iron_jelly.model.entity.Order;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class CardDTO extends BaseDTO {

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UUID userId;
    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UUID cardTemplateId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    boolean isActive;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UserDTO user;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private CardTemplateDTO cardTemplate;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer countOrders;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private HashSet<Order> orders;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate expireDate;
}
