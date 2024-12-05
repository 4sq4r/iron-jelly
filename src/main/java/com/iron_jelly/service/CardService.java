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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final CardMapper cardMapper;

    public CardDTO saveOne(CardDTO cardDTO) {
        Card card = cardMapper.toEntity(cardDTO);
        Card savedCard = cardRepository.save(card);
        String formattedCardNumber = String.format("%08d", savedCard.getCardNumber());
        cardDTO.setCardNumber(Integer.valueOf(formattedCardNumber));

        return cardMapper.toDTO(savedCard);
    }

    public CardDTO getOne(UUID id) {
        return cardMapper.toDTO(findById(id));
    }

    public void deleteOne(UUID id) {
        Card card = findById(id);
        cardRepository.delete(card);
    }

    public Card findById(UUID id) {
        return cardRepository.findById(id).orElseThrow(
                () -> CustomException.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message(MessageSource.CARD_NOT_FOUND.getText())
                        .build());
    }
}
