package com.iron_jelly.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class SalesPointDTO extends BaseDTO {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UUID companyId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private CompanyDTO companyDTO;
}
