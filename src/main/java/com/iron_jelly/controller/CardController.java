package com.iron_jelly.controller;

import com.iron_jelly.model.dto.CardDTO;
import com.iron_jelly.model.dto.ExtendExpirationRequestDTO;
import com.iron_jelly.service.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/cards/v1")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping
    public CardDTO saveOne(@RequestBody @Valid CardDTO cardDTO) {
        log.info("Incoming request to save card.");
        return cardService.saveOne(cardDTO);
    }

    @GetMapping("/{id}")
    public CardDTO getOne(@PathVariable UUID id) {
        log.info("Incoming request to get card with id: {}", id);
        return cardService.getOne(id);
    }

    @DeleteMapping("/{id}")
    public void deleteOne(@PathVariable UUID id) {
        log.info("Incoming request to delete card with id: {}", id);
        cardService.deleteOne(id);
    }

    @PostMapping("/extend-expiration")
    public ResponseEntity<Void> extendExpirationDate(@RequestBody ExtendExpirationRequestDTO request) {
        cardService.extendExpirationDate(request.getDays(), request.getId());
        return ResponseEntity.noContent().build();
    }


}
