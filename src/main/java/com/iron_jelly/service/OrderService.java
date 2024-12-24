package com.iron_jelly.service;

import java.util.UUID;
import com.iron_jelly.model.dto.OrderRequestDTO;
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

    public void saveOne(OrderRequestDTO orderRequestDTO) {
        UUID cardExternalId = orderRequestDTO.getCardExternalId();
        UUID salesPointExternalId = orderRequestDTO.getSalesPointExternalId();
        Card card = cardService.findByExternalId(cardExternalId);

        if(!card.getCardTemplate().getSalesPoint().getExternalId().equals(salesPointExternalId)) {
            throw CustomException.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message(MessageSource.THIS_CARD_DOES_NOT_BELONG_TO_THIS_POINT_OF_SALE.getText())
                    .build();
        }

        if(!card.getActive()) {
            throw CustomException.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message(MessageSource.CARD_NOT_ACTIVE.getText())
                    .build();
        }

        Order order = new Order();
        order.setCard(card);
        order.setIsFree(checkForFreeOrderAndDeactivateCardIfFree(order, card));
        String username = jwtService.getUsername();
        order.setCreatedBy(username);
        order.setUpdatedBy(username);
        orderRepository.save(order);
        cardService.addOrderToCard(card, order);
    }

    public boolean checkForFreeOrderAndDeactivateCardIfFree(Order order, Card card) {
        int countOrdersInCard = card.getOrders().size();
        int limitValueInCardTemplate = order.getCard().getCardTemplate().getLimitValue();

        if (countOrdersInCard == limitValueInCardTemplate) {
            cardService.deactivateCard(card);
            Card newCard = cardService.createNewCardWhenOldIsDeactivated(card);
            System.out.println("Старая карта деактивирована, создана новая карта с ID: " + newCard.getExternalId());

            return true;
        }

        return false;
    }
}
