package com.iron_jelly.mapper;

import com.iron_jelly.model.dto.CardTemplateDTO;
import com.iron_jelly.model.entity.CardTemplate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CardTemplateMapper {

    CardTemplateDTO toDTO(CardTemplate entity);

    CardTemplate toEntity(CardTemplateDTO dto);
}
