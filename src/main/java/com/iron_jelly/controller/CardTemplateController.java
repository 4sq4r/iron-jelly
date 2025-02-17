package com.iron_jelly.controller;

import com.iron_jelly.model.dto.CardTemplateDTO;
import com.iron_jelly.service.CardTemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/card-templates/v1")
@RequiredArgsConstructor
public class CardTemplateController {

    private final CardTemplateService cardTemplateService;

    @Operation(summary = "Сохранить шаблон карты лояльности")
    @ApiResponse(responseCode = "200", description = "метод для сохранения шаблона карты лояльности")
    @PostMapping
    public CardTemplateDTO saveOne(@RequestBody @Valid CardTemplateDTO cardTemplateDTO) {
        log.info("Incoming request to save card template: {}", cardTemplateDTO.getTitle());
        return cardTemplateService.saveOne(cardTemplateDTO);
    }

    @Operation(summary = "Получение шаблона карты лояльности по UUID")
    @ApiResponse(responseCode = "201", description = "метод дляполучение шаблона карты лояльности по UUID")
    @GetMapping("/{externalId}")
    public CardTemplateDTO getOne(@PathVariable UUID externalId) {
        log.info("Incoming request to get card template with externalId: {}", externalId);
        return cardTemplateService.getOne(externalId);
    }

    @Operation(summary = "Удаление шаблона карты лояльности")
    @ApiResponse(responseCode = "204", description = "метод для удаления шаблона карты лояльности")
    @DeleteMapping("/{externalId}")
    public void deleteOne(@PathVariable UUID externalId) {
        log.info("Incoming request to delete user with id: {}", externalId);
        cardTemplateService.deleteOne(externalId);
    }

    @Operation(summary = "Деактивировать шаблон карты лояльности")
    @ApiResponse(responseCode = "204", description = "метод для деактивации шаблона карты лояльности")
    @PutMapping("/{externalId}/deactivate")
    public void deactivateCardTemplate(@PathVariable UUID externalId) {
        cardTemplateService.deactivateCardTemplate(externalId);
    }
}
