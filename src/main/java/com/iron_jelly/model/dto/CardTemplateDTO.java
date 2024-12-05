package com.iron_jelly.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class CardTemplateDTO extends BaseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotNull
    private long cardTemplateId;
    @NotNull
    private UUID companyId;
    @NotNull
    private String name;
    @NotNull
    private int limit;
    @NotNull
    private int expireDays;
    @NotNull
    private String description;
}
