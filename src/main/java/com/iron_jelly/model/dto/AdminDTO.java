package com.iron_jelly.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class AdminDTO extends UserDTO {

    private String company;
    private UUID companyId;
}
