package com.iron_jelly.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CardTemplateDTO extends BaseDTO {

    @NotNull
    private Long companyId;
    @NotNull
    private String name;
    @NotNull
    private Integer limit;
    @NotNull
    private Integer expireDays;
    @NotNull
    private String description;
}
