package com.iron_jelly.model.dto;

import jakarta.validation.constraints.NotNull;

public class OrderDTO extends BaseDTO {

    @NotNull
    boolean isFree = false;
}
