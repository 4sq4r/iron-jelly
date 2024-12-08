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
    private final CardService cardService;

    public OrderDTO saveOne(OrderDTO orderDTO) {
        Order order = orderMapper.toEntity(orderDTO);
        Order savedOrder = orderRepository.save(order);

        return orderMapper.toDTO(savedOrder);
    }

    public OrderDTO getOne(Long id) {
        return orderMapper.toDTO(findById(id));
    }

    private Order findById(Long id) {
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
    public void useFreeOrder(Order order) {
        Order existingOrder = orderRepository.findById(order.getId()).orElseThrow(() ->
                CustomException.builder()
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .message("No order found with the provided ID.")
                        .build()
        );

        if (!existingOrder.isFree()) {
            throw CustomException.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message("This order is not marked as free.")
                    .build();
        }

        existingOrder.setFree(false);
        orderRepository.save(existingOrder);

        Card card = order.getCard();
        card.setActive(false);
        cardService.deleteOne(card.getId());
    }
}
