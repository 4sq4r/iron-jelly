package com.iron_jelly.mapper;

import com.iron_jelly.model.dto.OrderDTO;
import com.iron_jelly.model.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDTO toDTO(Order entity);

    Order toEntity(OrderDTO dto);
}
