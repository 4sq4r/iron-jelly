package com.iron_jelly.controller;

import com.iron_jelly.model.dto.UserDTO;
import com.iron_jelly.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/users/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserDTO saveOne(@RequestBody @Valid UserDTO userDTO) {
        log.info("Incoming request to save user: {}", userDTO.getUsername());
        return userService.saveOne(userDTO);
    }

    @GetMapping("/{id}")
    public UserDTO getOne(@PathVariable long id) {
        log.info("Incoming request to get user with id: {}", id);
        return userService.getOne(id);
    }

    @DeleteMapping("/{id}")
    public void deleteOne(@PathVariable long id) {
        log.info("Incoming request to delete user with id: {}", id);
        userService.deleteOne(id);
    }
}
