package com.iron_jelly.controller;

import com.iron_jelly.model.dto.CardDTO;
import com.iron_jelly.model.dto.ExtendExpirationRequestDTO;
import com.iron_jelly.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/cards/v1")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @Operation(summary = "Сохранение карты лояльности")
    @ApiResponse(responseCode = "201", description = "метод для сохранения карты лояльности")
    @PostMapping
    public CardDTO saveOne(@RequestBody @Valid CardDTO cardDTO) {
        log.info("Incoming request to save card.");
        return cardService.saveOne(cardDTO);
    }

    @Operation(summary = "Получение карты лояльности по UUID")
    @ApiResponse(responseCode = "200", description = "метод для получения карты лояльности по UUID")
    @ApiResponse(responseCode = "400", description = "карта не найдена")
    @GetMapping("/{id}")
    public CardDTO getOne(@PathVariable UUID id) {
        log.info("Incoming request to get card with id: {}", id);
        return cardService.getOne(id);
    }

    @Operation(summary = "Удаление карты лояльности")
    @ApiResponse(responseCode = "204", description = "метод для удаления карты лояльности")
    @ApiResponse(responseCode = "400", description = "карта не найдена")
    @DeleteMapping("/{id}")
    public void deleteOne(@PathVariable UUID id) {
        log.info("Incoming request to delete card with id: {}", id);
        cardService.deleteOne(id);
    }

    @Operation(summary = "Продление срок действия карты лояльности(метод у продавца)")
    @ApiResponse(responseCode = "204", description = "метод продлевает срок действия карты лояльности(метод у продавца)")
    @ApiResponse(responseCode = "400", description = "карта не найдена")
    @PostMapping("/extend-expiration")
    public ResponseEntity<Void> extendExpirationDate(@RequestBody ExtendExpirationRequestDTO request) {
        cardService.extendExpirationDate(request.getDays(), request.getId());
        return ResponseEntity.noContent().build();
    }

    @Operation( summary = "Получает активные карты у клиента")
    @ApiResponse(responseCode = "200", description = "метод для получение активных карт лояльности у пользователя")
    @GetMapping("/{id}/cards")
    public ResponseEntity<List<CardDTO>> getUserActiveCards (@PathVariable UUID id) {
        List<CardDTO> cards = cardService.getUserActiveCards(id);
        return ResponseEntity.ok(cards);
    }

}
