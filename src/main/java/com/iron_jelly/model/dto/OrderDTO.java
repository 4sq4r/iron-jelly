package com.iron_jelly.model.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public class OrderDTO extends BaseDTO {

    @NotNull
    private long id;
    @NotNull
    private UUID cardId;
    @NotNull
    boolean isFree = false;
}
