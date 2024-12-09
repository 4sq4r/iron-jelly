package com.iron_jelly.service;

import com.iron_jelly.exception.CustomException;
import com.iron_jelly.mapper.CardTemplateMapper;
import com.iron_jelly.model.dto.CardTemplateDTO;
import com.iron_jelly.model.entity.CardTemplate;
import com.iron_jelly.model.entity.Company;
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
    private final CompanyService companyService;

    public CardTemplateDTO saveOne(CardTemplateDTO cardTemplateDTO) {

        Company company = companyService.findEntityByExternalId(cardTemplateDTO.getExternalId());
        CardTemplate cardTemplate = cardTemplateMapper.toEntity(cardTemplateDTO);
        cardTemplate.setCompany(company);
        CardTemplate savedCardTemplate = cardTemplateRepository.save(cardTemplate);

        return cardTemplateMapper.toDTO(savedCardTemplate);
    }

    public CardTemplateDTO getOne(Long id) {
        return cardTemplateMapper.toDTO(findById(id));
    }

    public void deleteOne(Long id) {
        CardTemplate cardTemplate = findById(id);
        cardTemplateRepository.delete(cardTemplate);
    }

    private CardTemplate findById(Long id) {
        return cardTemplateRepository.findById(id).orElseThrow(
                () -> CustomException.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message(MessageSource.CARD_TEMPLATE_NOT_FOUND.getText())
                        .build());
    }
}
