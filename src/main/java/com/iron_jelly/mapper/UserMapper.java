package com.iron_jelly.mapper;

import com.iron_jelly.model.dto.UserDTO;
import com.iron_jelly.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDTO(User entity);

    User toEntity(UserDTO dto);
}
