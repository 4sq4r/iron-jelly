package com.iron_jelly.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class CardTemplateDTO extends BaseDTO {

    @NotNull
    private String title;
    @NotNull
    private Integer limitValue;
    @NotNull
    private Integer expireDays;
    @NotNull
    private String description;
    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UUID companyId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private CompanyDTO company;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean active;
}
