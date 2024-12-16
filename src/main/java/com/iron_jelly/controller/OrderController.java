package com.iron_jelly.controller;

import com.iron_jelly.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/orders/v1")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public void saveOne(@RequestBody @Valid UUID externalId) {
        log.info("Incoming request to save order.");
        orderService.saveOne(externalId);
    }
}
