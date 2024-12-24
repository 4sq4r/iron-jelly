package com.iron_jelly.controller;

import com.iron_jelly.model.dto.OrderRequestDTO;
import com.iron_jelly.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/orders/v1")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public void saveOne(@RequestBody @Valid OrderRequestDTO orderRequestDTO) {
        log.info("Incoming request to save order.");
        orderService.saveOne(orderRequestDTO);
    }
}
