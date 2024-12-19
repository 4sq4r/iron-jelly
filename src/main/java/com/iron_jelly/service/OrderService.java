package com.iron_jelly.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.iron_jelly.model.entity.Card;
import com.iron_jelly.model.entity.Order;
import com.iron_jelly.util.MessageSource;
import com.iron_jelly.security.JwtService;
import com.iron_jelly.exception.CustomException;
import com.iron_jelly.repository.OrderRepository;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CardService cardService;
    private final JwtService jwtService;

    public void saveOne(UUID externalId) {
        Card card = cardService.findByExternalId(externalId);

        if(!card.getActive()) {
            throw CustomException.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message(MessageSource.CARD_NOT_ACTIVE.getText())
                    .build();
        }

        Order order = new Order();
        order.setCard(card);
        order.setIsFree(setOrderFieldIsActiveFalseOrFree(order, card));
        String username = jwtService.getUsername();
        order.setCreatedBy(username);
        order.setUpdatedBy(username);
        orderRepository.save(order);
        cardService.addOrderToCard(card, order);
    }

    public boolean setOrderFieldIsActiveFalseOrFree(Order order, Card card) {
        int countOrdersInCard = card.getOrders().size();
        int limitValueInCardTemplate = order.getCard().getCardTemplate().getLimitValue();

        if (countOrdersInCard == limitValueInCardTemplate) {
            cardService.deactivateCard(card);
            return true;
        }

        return false;
    }
}
