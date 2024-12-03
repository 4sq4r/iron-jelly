package com.iron_jelly.controller;

import com.iron_jelly.model.dto.CardTemplateDTO;
import com.iron_jelly.service.CardTemplateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/cardTemplates/v1")
@RequiredArgsConstructor
public class CardTemplateController {

    private final CardTemplateService cardTemplateService;

    @PostMapping
    public CardTemplateDTO saveOne(@RequestBody @Valid CardTemplateDTO cardTemplateDTO) {
        log.info("Incoming request to save card template: {}", cardTemplateDTO.getName());
        return cardTemplateService.saveOne(cardTemplateDTO);
    }

    @GetMapping("/{id}")
    public CardTemplateDTO getOne(@PathVariable long id) {
        log.info("Incoming request to get card template with id: {}", id);
        return cardTemplateService.getOne(id);
    }

    @DeleteMapping("/{id}")
    public void deleteOne(@PathVariable long id) {
        log.info("Incoming request to delete user with id: {}", id);
        cardTemplateService.deleteOne(id);
    }
}
