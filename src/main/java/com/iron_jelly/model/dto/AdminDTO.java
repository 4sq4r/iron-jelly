package com.iron_jelly.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AdminDTO extends UserDTO {

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String companyName;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private CompanyDTO companyDTO;
}
