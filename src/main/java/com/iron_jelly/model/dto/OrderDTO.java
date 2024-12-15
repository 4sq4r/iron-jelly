package com.iron_jelly.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderDTO extends BaseDTO {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UUID cardId;
    boolean isFree;
}
