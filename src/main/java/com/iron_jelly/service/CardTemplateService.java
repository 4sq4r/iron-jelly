package com.iron_jelly.service;

import com.iron_jelly.exception.CustomException;
import com.iron_jelly.mapper.CardTemplateMapper;
import com.iron_jelly.model.dto.CardTemplateDTO;
import com.iron_jelly.model.entity.CardTemplate;
import com.iron_jelly.repository.CardTemplateRepository;
import com.iron_jelly.util.MessageSource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardTemplateService {

    private final CardTemplateRepository cardTemplateRepository;
    private final CardTemplateMapper cardTemplateMapper;

    public CardTemplateDTO saveOne(CardTemplateDTO cardTemplateDTO) {
        CardTemplate cardTemplate = cardTemplateMapper.toEntity(cardTemplateDTO);
        cardTemplate.setName(cardTemplate.getName());
        cardTemplate.setLimit(cardTemplate.getLimit());
        cardTemplate.setExpireDays(cardTemplate.getExpireDays());
        cardTemplate.setDescription(cardTemplateDTO.getDescription());

        return cardTemplateMapper.toDTO(cardTemplate);
    }

    public CardTemplateDTO getOne(long id) {
        return cardTemplateMapper.toDTO(findById(id));
    }

    public void deleteOne(long id) {
        CardTemplate cardTemplate = findById(id);
        cardTemplateRepository.delete(cardTemplate);
    }

    private CardTemplate findById(long id) {
        return cardTemplateRepository.findById(id).orElseThrow(
                () -> CustomException.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message(MessageSource.CARD_TEMPLATE_NOT_FOUND.getText())
                        .build());
    }
}
