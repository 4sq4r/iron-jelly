package com.iron_jelly.controller;

import com.iron_jelly.model.dto.OrderRequestDTO;
import com.iron_jelly.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "Сохранение заказа")
    @ApiResponse(responseCode = "204", description = "метод для сохранения заказа")
    @PostMapping
    public void saveOne(@RequestBody @Valid OrderRequestDTO orderRequestDTO) {
        log.info("Incoming request to save order.");
        orderService.saveOne(orderRequestDTO);
    }
}
