package com.iron_jelly.service;

import com.iron_jelly.exception.CustomException;
import com.iron_jelly.mapper.CardTemplateMapper;
import com.iron_jelly.model.dto.CardTemplateDTO;
import com.iron_jelly.model.entity.CardTemplate;
import com.iron_jelly.model.entity.SalesPoint;
import com.iron_jelly.repository.CardTemplateRepository;
import com.iron_jelly.security.JwtService;
import com.iron_jelly.util.MessageSource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardTemplateService {

    private final CardTemplateRepository cardTemplateRepository;
    private final CardTemplateMapper cardTemplateMapper;
    private final JwtService jwtService;
    private final SalesPointService salesPointService;


    public CardTemplateDTO saveOne(CardTemplateDTO cardTemplateDTO) {

        String username = jwtService.getUsername();

        CardTemplate cardTemplate = cardTemplateMapper.toEntity(cardTemplateDTO);
        SalesPoint salesPoint = salesPointService.findEntityByExternalId(cardTemplateDTO.getSalesPointId());
        cardTemplate.setSalesPoint(salesPoint);
        cardTemplate.setCreatedBy(username);
        cardTemplate.setUpdatedBy(username);
        cardTemplate.setActive(true);
        salesPoint.getCardTemplates().add(cardTemplate);
        cardTemplateRepository.save(cardTemplate);
        cardTemplateDTO = cardTemplateMapper.toDTO(cardTemplate);

        return cardTemplateDTO;
    }

    public CardTemplateDTO getOne(UUID externalId) {
        return cardTemplateMapper.toDTO(findByExternalId(externalId));
    }

    public void deleteOne(UUID externalId) {
        CardTemplate cardTemplate = findByExternalId(externalId);
        cardTemplateRepository.delete(cardTemplate);
    }

    public CardTemplate findByExternalId(UUID externalId) {
        return cardTemplateRepository.findByExternalId(externalId).orElseThrow(() -> CustomException.builder().httpStatus(HttpStatus.BAD_REQUEST).message(MessageSource.CARD_TEMPLATE_NOT_FOUND.getText()).build());
    }

    public void deactivateCardTemplate(UUID externalId) {
        CardTemplate cardTemplate = cardTemplateRepository.findByExternalId(externalId).orElseThrow(() -> CustomException.builder().httpStatus(HttpStatus.BAD_REQUEST).message(MessageSource.CARD_TEMPLATE_NOT_FOUND.getText()).build());
        cardTemplate.setActive(false);
        cardTemplateRepository.save(cardTemplate);
    }
}
