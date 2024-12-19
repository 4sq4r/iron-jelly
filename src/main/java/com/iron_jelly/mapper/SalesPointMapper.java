package com.iron_jelly.mapper;

import com.iron_jelly.model.dto.SalesPointDTO;
import com.iron_jelly.model.entity.SalesPoint;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SalesPointMapper {

    SalesPointDTO toDTO(SalesPoint entity);

    SalesPoint toEntity(SalesPointDTO dto);
}
