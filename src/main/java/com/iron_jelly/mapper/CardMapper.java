package com.iron_jelly.mapper;

import com.iron_jelly.model.dto.CardDTO;
import com.iron_jelly.model.entity.Card;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface CardMapper {

    CardDTO toDTO(Card entity);

    Card toEntity(CardDTO dto);
}
