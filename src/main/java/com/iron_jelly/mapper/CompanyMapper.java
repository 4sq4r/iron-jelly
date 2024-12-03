package com.iron_jelly.mapper;

import com.iron_jelly.model.dto.CompanyDTO;
import com.iron_jelly.model.entity.Company;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    CompanyDTO toDTO(Company entity);

    Company toEntity(CompanyDTO dto);
}
