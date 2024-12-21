package com.iron_jelly.controller;

import com.iron_jelly.model.dto.SalesPointDTO;
import com.iron_jelly.service.SalesPointService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/sales_point/v1")
@RequiredArgsConstructor
public class SalesPointController {

    private final SalesPointService salesPointService;

    @PostMapping
    public SalesPointDTO saveOne(@RequestBody @Valid SalesPointDTO salesPointDTO) {
        log.info("Incoming request to save sales point.");
        salesPointService.saveOne(salesPointDTO);
        return salesPointService.saveOne(salesPointDTO);
    }
}
