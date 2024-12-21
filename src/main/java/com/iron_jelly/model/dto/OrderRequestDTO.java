package com.iron_jelly.model.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderRequestDTO {

    @NotNull
    private UUID cardExternalId;

    @NotNull
    private UUID salesPointExternalId;
}
