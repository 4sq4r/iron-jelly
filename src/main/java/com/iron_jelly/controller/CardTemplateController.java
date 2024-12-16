package com.iron_jelly.controller;

import com.iron_jelly.model.dto.CardTemplateDTO;
import com.iron_jelly.service.CardTemplateService;
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

    @PostMapping
    public CardTemplateDTO saveOne(@RequestBody @Valid CardTemplateDTO cardTemplateDTO) {
        log.info("Incoming request to save card template: {}", cardTemplateDTO.getTitle());
        return cardTemplateService.saveOne(cardTemplateDTO);
    }

    @GetMapping("/{externalId}")
    public CardTemplateDTO getOne(@PathVariable UUID externalId) {
        log.info("Incoming request to get card template with externalId: {}", externalId);
        return cardTemplateService.getOne(externalId);
    }

    @DeleteMapping("/{externalId}")
    public void deleteOne(@PathVariable UUID externalId) {
        log.info("Incoming request to delete user with id: {}", externalId);
        cardTemplateService.deleteOne(externalId);
    }
}
