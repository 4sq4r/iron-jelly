package com.iron_jelly.service;

import com.iron_jelly.exception.CustomException;
import com.iron_jelly.mapper.CardMapper;
import com.iron_jelly.model.dto.CardDTO;
import com.iron_jelly.model.entity.Card;
import com.iron_jelly.repository.CardRepository;
import com.iron_jelly.util.MessageSource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final CardMapper cardMapper;
    private final OrderService orderService;
    private final UserService userService;
    public final CompanyService companyService;

    public CardDTO saveOne(CardDTO cardDTO) {
        userService.findById(cardDTO.getUserId());
        companyService.findEntityByExternalId(cardDTO.getExternalId());

        Card card = cardMapper.toEntity(cardDTO);
        Card savedCard = cardRepository.save(card);

        return cardMapper.toDTO(savedCard);
    }

    public CardDTO getOne(Long id) {
        return cardMapper.toDTO(findById(id));
    }

    public void deleteOne(Long id) {
        cardRepository.delete(findById(id));
    }

    public Card findById(Long id) {
        return cardRepository.findById(id).orElseThrow(
                () -> CustomException.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message(MessageSource.CARD_NOT_FOUND.getText())
                        .build());
    }

    public void deactivateCard(Card card) {
        card.setActive(false);
    }

    public void useCard(Card card) {
        if (!card.isActive()) {
            throw new IllegalStateException("Card is not active and cannot be used.");
        }

        int currentLimit = card.getUsageLimit();
        card.setUsageLimit(currentLimit - 1);

        if (card.getUsageLimit() == 0) {
            deactivateCard(card);
            orderService.createFreeOrder(card);
        }
    }
}
