package com.iron_jelly.service;

import com.iron_jelly.exception.CustomException;
import com.iron_jelly.mapper.OrderMapper;
import com.iron_jelly.model.dto.OrderDTO;
import com.iron_jelly.model.entity.Card;
import com.iron_jelly.model.entity.Order;
import com.iron_jelly.repository.OrderRepository;
import com.iron_jelly.util.MessageSource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private Order findById(long id) {
        return orderRepository.findById(id).orElseThrow(
                () -> CustomException.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message(MessageSource.ORDER_NOT_FOUND.getText())
                        .build());
    }

    public Order createFreeOrder(Card card) {
        Order order = new Order();
        order.setCard(card);
        order.setFree(true);

        return orderRepository.save(order);
    }

    @Transactional
    public void giveFreeOrder(Order order) {
        // Найти заказ по ID
        Order existingOrder = orderRepository.findById(order.getId()).orElseThrow(() ->
                CustomException.builder()
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .message("No order found with the provided ID.")
                        .build()
        );

        // Проверить, является ли заказ бесплатным
        if (!existingOrder.isFree()) {
            throw CustomException.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message("This order is not marked as free.")
                    .build();
        }

        // Обновить статус заказа на не бесплатный
        existingOrder.setFree(false);

        // Сохранить изменения
        orderRepository.save(existingOrder);
    }
}
