package com.iron_jelly.controller;

import com.iron_jelly.model.dto.CompanyDTO;
import com.iron_jelly.service.CompanyService;
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

    @PostMapping
    public CompanyDTO saveOne(@RequestBody @Valid CompanyDTO companyDTO) {
        log.info("Incoming request to save company: {}", companyDTO.getName());
        return companyService.saveOne(companyDTO);
    }

    @GetMapping("/{id}")
    public CompanyDTO getOne(@PathVariable Long id) {
        log.info("Incoming request to get company with id: {}", id);
        return companyService.getOne(id);
    }

    @DeleteMapping("/{id}")
    public void deleteOne(@PathVariable Long id) {
        log.info("Incoming request to delete company with id: {}", id);
        companyService.deleteOne(id);
    }
}
