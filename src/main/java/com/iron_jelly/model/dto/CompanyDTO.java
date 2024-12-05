package com.iron_jelly.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class CompanyDTO extends BaseDTO {

    @NotNull
    private String name;
}
