package com.iron_jelly.service;

import com.iron_jelly.exception.CustomException;
import com.iron_jelly.mapper.OrderMapper;
import com.iron_jelly.model.dto.OrderDTO;
import com.iron_jelly.model.entity.Order;
import com.iron_jelly.repository.OrderRepository;
import com.iron_jelly.util.MessageSource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderDTO saveOne(OrderDTO orderDTO) {
        Order order = orderMapper.toEntity(orderDTO);
        Order savedOrder = orderRepository.save(order);

        return orderMapper.toDTO(savedOrder);
    }

    public OrderDTO getOne(long id) {
        return orderMapper.toDTO(findById(id));
    }

    public void deleteOne(long id) {
        Order order = findById(id);
        orderRepository.delete(order);
    }

    private Order findById(long id) {
        return orderRepository.findById(id).orElseThrow(
                () -> CustomException.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message(MessageSource.ORDER_NOT_FOUND.getText())
                        .build());
    }
}
