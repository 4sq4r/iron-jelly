package com.iron_jelly.service;

import com.iron_jelly.exception.CustomException;
import com.iron_jelly.mapper.OrderMapper;
import com.iron_jelly.model.dto.OrderDTO;
import com.iron_jelly.model.entity.Card;
import com.iron_jelly.model.entity.Order;
import com.iron_jelly.repository.CardRepository;
import com.iron_jelly.repository.OrderRepository;
import com.iron_jelly.security.JwtService;
import com.iron_jelly.util.MessageSource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CardService cardService;
    private final JwtService jwtService;
    private final CardRepository cardRepository;


    public OrderDTO saveOne(OrderDTO orderDTO) {
        Order order = orderMapper.toEntity(orderDTO);
        Card card = cardService.findByExternalId(orderDTO.getCardId());
        if(card.isActive()) {
            order.setCard(card);
            order.setFree(setOrderFieldIsActiveFalseOrFree(order, card));
            String username = jwtService.getUsername();
            order.setCreatedBy(username);
            order.setUpdatedBy(username);
            orderRepository.save(order);
            cardService.addOrderToCard(card, order);

            return orderMapper.toDTO(order);
        } else throw CustomException.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(MessageSource.CARD_NOT_ACTIVE.getText())
                .build();
    }

    public boolean setOrderFieldIsActiveFalseOrFree(Order order, Card card) {
        int countOrdersInCard = order.getCard().getCountOrders();
        int limitValueInCardTemplate = order.getCard().getCardTemplate().getLimitValue();
        if (countOrdersInCard == limitValueInCardTemplate) {
            cardService.deactivateCard(card);
            return true;
        }
        return false;
    }
}
