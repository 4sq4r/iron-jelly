package com.iron_jelly.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExtendExpirationRequestDTO {
    private int days;
    private long id;
}