package com.iron_jelly.service;

import com.iron_jelly.exception.CustomException;
import com.iron_jelly.model.dto.CardDTO;
import com.iron_jelly.model.dto.OrderRequestDTO;
import com.iron_jelly.model.entity.Card;
import com.iron_jelly.model.entity.Order;
import com.iron_jelly.repository.OrderRepository;
import com.iron_jelly.security.JwtService;
import com.iron_jelly.util.MessageSource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

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

        if (!card.getCardTemplate().getSalesPoint().getExternalId().equals(salesPointExternalId)) {
            throw CustomException.builder().httpStatus(HttpStatus.BAD_REQUEST).message(MessageSource.THIS_CARD_DOES_NOT_BELONG_TO_THIS_POINT_OF_SALE.getText()).build();
        }

        if (!card.getActive()) {
            throw CustomException.builder().httpStatus(HttpStatus.BAD_REQUEST).message(MessageSource.CARD_NOT_ACTIVE.getText()).build();
        }

        Order order = new Order();
        order.setCard(card);
        order.setIsFree(checkForFreeOrderAndDeactivateCardIfFreeAndCreateNewOne(order, card));
        String username = jwtService.getUsername();
        order.setCreatedBy(username);
        order.setUpdatedBy(username);
        orderRepository.save(order);
        cardService.addOrderToCard(card, order);
    }

    public boolean checkForFreeOrderAndDeactivateCardIfFreeAndCreateNewOne(Order order, Card card) {
        int countOrdersInCard = card.getOrders().size();
        int limitValueInCardTemplate = order.getCard().getCardTemplate().getLimitValue();

        if (countOrdersInCard == limitValueInCardTemplate) {
            cardService.deactivateCard(card);
            CardDTO newCardDTO = new CardDTO();
            newCardDTO.setUserId(card.getUser().getExternalId());
            newCardDTO.setCardTemplateId(card.getCardTemplate().getExternalId());
            cardService.saveOne(newCardDTO);
            System.out.println("Старая карта деактивирована, создана новая карта с ID: " + newCardDTO.getExternalId());

            return true;
        }

        return false;
    }
}
