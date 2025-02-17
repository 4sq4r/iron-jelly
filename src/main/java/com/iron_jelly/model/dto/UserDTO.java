package com.iron_jelly.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends BaseDTO {

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Некорректный номер телефона")
    private String phoneNumber;
    @NotNull
    @Email(message = "Некорректный формат email")
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UUID companyId;
}
