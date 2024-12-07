package com.iron_jelly.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class CardTemplateDTO extends BaseDTO {

    @NotNull
    private Long companyId;
    @NotNull
    private String name;
    @NotNull
    private int limit;
    @NotNull
    private int expireDays;
    @NotNull
    private String description;
}
