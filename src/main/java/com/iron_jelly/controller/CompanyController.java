package com.iron_jelly.controller;

import com.iron_jelly.model.dto.CompanyDTO;
import com.iron_jelly.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/companies/v1")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @Operation(summary = "Сохранить компанию")
    @ApiResponse(responseCode = "200", description = "метод для сохранения компании")
    @PostMapping
    public CompanyDTO saveOne(@RequestBody @Valid CompanyDTO companyDTO) {
        log.info("Incoming request to save company: {}", companyDTO.getName());
        return companyService.saveOne(companyDTO);
    }

    @Operation(summary = "Получить компанию по UUID")
    @ApiResponse(responseCode = "201", description = "метод для получения компании по UUID")
    @GetMapping("/{id}")
    public CompanyDTO getOne(@PathVariable UUID id) {
        log.info("Incoming request to get company with id: {}", id);
        return companyService.getOne(id);
    }

    @Operation(summary = "Удалить компанию")
    @ApiResponse(responseCode = "204", description = "метод для удаления компании")
    @DeleteMapping("/{id}")
    public void deleteOne(@PathVariable UUID id) {
        log.info("Incoming request to delete company with id: {}", id);
        companyService.deleteOne(id);
    }

    @Operation(summary = "Обновить инфу о компании")
    @ApiResponse(responseCode = "204", description = "метод для обновления инфы о компании")
    @PutMapping("/{id}")
    public CompanyDTO updateOne(@PathVariable UUID id, @RequestBody @Valid CompanyDTO companyDTO) {
        return companyService.updateOne(id, companyDTO);
    }
}
