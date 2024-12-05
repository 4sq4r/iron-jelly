package com.iron_jelly.service;

import com.iron_jelly.exception.CustomException;
import com.iron_jelly.mapper.CardTemplateMapper;
import com.iron_jelly.model.dto.CardTemplateDTO;
import com.iron_jelly.model.entity.CardTemplate;
import com.iron_jelly.model.entity.Company;
import com.iron_jelly.repository.CardTemplateRepository;
import com.iron_jelly.repository.CompanyRepository;
import com.iron_jelly.util.MessageSource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardTemplateService {

    private final CardTemplateRepository cardTemplateRepository;
    private final CardTemplateMapper cardTemplateMapper;
    private final CompanyRepository companyRepository;

    public CardTemplateDTO saveOne(CardTemplateDTO cardTemplateDTO) {
        Company company = companyRepository.findById(cardTemplateDTO.getCompanyId())
                .orElseThrow(() -> CustomException.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message("Компания с указанным ID не найдена.")
                        .build());

        CardTemplate cardTemplate = cardTemplateMapper.toEntity(cardTemplateDTO);
        cardTemplate.setCompany(company);
        cardTemplate.setName(cardTemplateDTO.getName());
        cardTemplate.setLimit(cardTemplateDTO.getLimit());
        cardTemplate.setExpireDays(cardTemplateDTO.getExpireDays());
        cardTemplate.setDescription(cardTemplateDTO.getDescription());
        CardTemplate savedCardTemplate = cardTemplateRepository.save(cardTemplate);

        return cardTemplateMapper.toDTO(savedCardTemplate);
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
