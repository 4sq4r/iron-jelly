package com.iron_jelly.controller;

import com.iron_jelly.model.dto.OrderDTO;
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
    public OrderDTO saveOne(@RequestBody @Valid OrderDTO orderDTO) {
        log.info("Incoming request to save order.");
        return orderService.saveOne(orderDTO);
    }

    @GetMapping("/{id}")
    public OrderDTO getOne(@PathVariable long id) {
        log.info("Incoming request to get order with id: {}", id);
        return orderService.getOne(id);
    }
}
