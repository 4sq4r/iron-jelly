package com.iron_jelly.service;

import com.iron_jelly.exception.CustomException;
import com.iron_jelly.mapper.CardMapper;
import com.iron_jelly.model.dto.CardDTO;
import com.iron_jelly.model.entity.Card;
import com.iron_jelly.model.entity.CardTemplate;
import com.iron_jelly.model.entity.Order;
import com.iron_jelly.model.entity.User;
import com.iron_jelly.repository.CardRepository;
import com.iron_jelly.security.JwtService;
import com.iron_jelly.util.MessageSource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final CardMapper cardMapper;
    private final UserService userService;
    private final CardTemplateService cardTemplateService;
    private final JwtService jwtService;


    public CardDTO saveOne(CardDTO cardDTO) {
        String username = jwtService.getUsername();

        Card card = cardMapper.toEntity(cardDTO);
        User user = userService.findEntityByExternalId(cardDTO.getUserId());
        card.setUser(user);
        CardTemplate cardTemplate = cardTemplateService.findByExternalId(cardDTO.getCardTemplateId());
        card.setCardTemplate(cardTemplate);
        card.setActive(true);
        card.setCountOrders(0);
        setExpirationDate(card);
        card.setCreatedBy(username);
        card.setUpdatedBy(username);
        cardRepository.save(card);

        return cardMapper.toDTO(card);
    }


    public CardDTO getOne(UUID id) {
        return cardMapper.toDTO(findByExternalId(id));
    }

    public void deleteOne(UUID id) {
        cardRepository.delete(findByExternalId(id));
    }

    public Card findByExternalId(UUID id) {
        return cardRepository.findByExternalId(id).orElseThrow(
                () -> CustomException.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message(MessageSource.CARD_NOT_FOUND.getText())
                        .build());
    }

    public Card findById(long id){
        return cardRepository.findById(id).orElseThrow(
                () -> CustomException.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message(MessageSource.CARD_NOT_FOUND.getText())
                        .build());
    }

    public void deactivateCard(Card card) {
        card.setActive(false);
    }

    public void addOrderToCard(Card card, Order order) {
        Set<Order> orders = card.getOrders();
        if (orders == null) {
            orders = new HashSet<>();
            card.setOrders(orders);
        }
        orders.add(order);
        card.setCountOrders(card.getCountOrders() + 1);
        cardRepository.save(card);
    }

    public void setExpirationDate(Card card) {
        LocalDate today = LocalDate.now();
        LocalDate expireDay = today.plusDays(card.getCardTemplate().getExpireDays());
        card.setExpireDate(expireDay);
    }

    public void extendExpirationDate(int days, long id) {
        Card card = findById(id);
        LocalDate newExpireDay = card.getExpireDate().plusDays(days);
        card.setExpireDate(newExpireDay);
        cardRepository.save(card);
    }
}


