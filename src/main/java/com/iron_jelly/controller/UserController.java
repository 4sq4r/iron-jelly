package com.iron_jelly.controller;

import com.iron_jelly.model.dto.AuthDTO;
import com.iron_jelly.model.dto.CardDTO;
import com.iron_jelly.model.dto.UserDTO;
import com.iron_jelly.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/users/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserDTO register(@RequestBody @Valid UserDTO userDTO) {
        log.info("Incoming request to save user: {}", userDTO.getUsername());
        return userService.saveOne(userDTO);
    }

    @PostMapping("/login")
    public AuthDTO login(@RequestBody @Valid AuthDTO authDTO) {
        return userService.login(authDTO);
    }

    @GetMapping("/get-cards")
    public Set<CardDTO> getUserCards() {
        return userService.getUserCards();
    }
}
