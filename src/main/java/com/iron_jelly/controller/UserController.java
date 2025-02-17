package com.iron_jelly.controller;

import com.iron_jelly.model.dto.AuthDTO;
import com.iron_jelly.model.dto.UserDTO;
import com.iron_jelly.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/users/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Регистрация пользователя")
    @ApiResponse(responseCode = "201", description = "метод для регистрации пользователя")
    @PostMapping
    public UserDTO register(@RequestBody @Valid UserDTO userDTO) {
        log.info("Incoming request to save user: {}", userDTO.getEmail());
        return userService.saveOne(userDTO);
    }

    @Operation(summary = "Логин пользователя")
    @ApiResponse(responseCode = "204", description = "метод для логина пользователя")
    @PostMapping("/login")
    public AuthDTO login(@RequestBody @Valid AuthDTO authDTO) {
        return userService.login(authDTO);
    }
}
