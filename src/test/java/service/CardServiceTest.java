package service;

import com.iron_jelly.exception.CustomException;
import com.iron_jelly.mapper.CardMapper;
import com.iron_jelly.model.dto.CardDTO;
import com.iron_jelly.model.entity.Card;
import com.iron_jelly.model.entity.CardTemplate;
import com.iron_jelly.model.entity.Order;
import com.iron_jelly.model.entity.User;
import com.iron_jelly.repository.CardRepository;
import com.iron_jelly.security.JwtService;
import com.iron_jelly.service.CardService;
import com.iron_jelly.service.CardTemplateService;
import com.iron_jelly.service.UserService;
import com.iron_jelly.util.MessageSource;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {
    private static final String USERNAME = "username";

    @Mock
    private CardTemplateService cardTemplateService;
    @Mock
    private JwtService jwtService;
    @Mock
    private UserService userService;
    @Mock
    private CardRepository cardRepository;
    @Spy
    private CardMapper cardMapper = Mappers.getMapper(CardMapper.class);
    @InjectMocks
    private CardService underTest;

    @Test
    void saveOne_throwsException_whenCardTemplateNotFound() {
        //given
        CardDTO cardDTO = Instancio.create(CardDTO.class);
        when(cardTemplateService.findByExternalId(cardDTO.getCardTemplateId())).thenThrow(CustomException.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(MessageSource.CARD_TEMPLATE_NOT_FOUND.getText())
                .build());
        //when
        CustomException exception = assertThrows(CustomException.class, () -> underTest.saveOne(cardDTO));
        //then
        assertNotNull(exception);
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals(MessageSource.CARD_TEMPLATE_NOT_FOUND.getText(), exception.getMessage());
    }

    @Test
    void saveOne_throwsException_whenCardTemplateIsNotActive() {
        // given
        CardDTO cardDTO = Instancio.create(CardDTO.class);
        CardTemplate cardTemplate = Instancio.create(CardTemplate.class);
        cardTemplate.setActive(false);
        when(cardTemplateService.findByExternalId(cardDTO.getCardTemplateId())).thenReturn(cardTemplate);
        // when
        CustomException exception = assertThrows(CustomException.class, () -> underTest.saveOne(cardDTO));
        // then
        assertNotNull(exception);
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals(MessageSource.CARD_TEMPLATE_NOT_ACTIVE.getText(), exception.getMessage());
    }

    @Test
    void saveOne_throwsException_whenUserNotFound() {
        //given
        CardDTO cardDTO = Instancio.create(CardDTO.class);
        CardTemplate cardTemplate = Instancio.create(CardTemplate.class);
        cardTemplate.setActive(true);
        when(cardTemplateService.findByExternalId(cardDTO.getCardTemplateId())).thenReturn(cardTemplate);
        when(userService.findEntityByExternalId(cardDTO.getUserId())).thenThrow(CustomException.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(MessageSource.USER_NOT_FOUND.getText())
                .build());
        //when
        CustomException exception = assertThrows(CustomException.class, () -> underTest.saveOne(cardDTO));
        //then
        assertNotNull(exception);
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals(MessageSource.USER_NOT_FOUND.getText(), exception.getMessage());
    }

    @Test
    void saveOne_savesCard() {
        //given
        ArgumentCaptor<Card> argumentCaptor = ArgumentCaptor.forClass(Card.class);
        Card card = Instancio.create(Card.class);
        User user = Instancio.create(User.class);
        CardDTO cardDTO = Instancio.of(CardDTO.class)
                .set(field(CardDTO::getActive), true)
                .create();
        cardDTO.setUserId(user.getExternalId());
        CardTemplate cardTemplate = Instancio.create(CardTemplate.class);
        cardTemplate.setActive(true);
        when(cardTemplateService.findByExternalId(cardDTO.getCardTemplateId())).thenReturn(cardTemplate);
        when(userService.findEntityByExternalId(cardDTO.getUserId())).thenReturn(user);
        when(cardRepository.save(any())).thenReturn(card);
        //when
        CardDTO result = underTest.saveOne(cardDTO);
        //then
        verify(cardRepository).save(argumentCaptor.capture());
        Card savedCard = argumentCaptor.getValue();
        assertNotNull(result);
        assertEquals(cardDTO.getActive(), savedCard.getActive());
    }

    @Test
    void getOne_throwsException_whenCardNotFound() {
        //given
        UUID id = UUID.randomUUID();
        when(cardRepository.findByExternalId(id)).thenReturn(Optional.empty());
        //when
        CustomException exception = assertThrows(CustomException.class, () -> underTest.getOne(id));
        //then
        assertNotNull(exception);
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals(MessageSource.CARD_NOT_FOUND.getText(), exception.getMessage());
    }

    @Test
    void deleteOne_throwsException_whenCardNotFound() {
        //given
        UUID id = UUID.randomUUID();
        when(cardRepository.findByExternalId(id)).thenReturn(Optional.empty()); // <-- исправлено
        //when
        CustomException exception = assertThrows(CustomException.class, () -> underTest.deleteOne(id));
        //then
        assertNotNull(exception);
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals(MessageSource.CARD_NOT_FOUND.getText(), exception.getMessage());
    }

    @Test
    void findByExternalId_throwsException_whenCardNotFound() {
        //given
        UUID id = UUID.randomUUID();
        //when
        CustomException exception = assertThrows(CustomException.class, () -> underTest.findByExternalId(id));
        //then
        assertNotNull(exception);
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals(MessageSource.CARD_NOT_FOUND.getText(), exception.getMessage());
    }

    @Test
    void findByExternalId_returnCard() {
        // given
        Card card = Instancio.create(Card.class);
        when(cardRepository.findByExternalId(card.getExternalId())).thenReturn(Optional.of(card));
        // when
        Card result = underTest.findByExternalId(card.getExternalId());
        //then
        assertEquals(card, result);
    }

    @Test
    void findById_throwsException_whenCardNotFound() {
        //given
        Long id = 1L;
        //when
        CustomException exception = assertThrows(CustomException.class, () -> underTest.findById(id));
        //then
        assertNotNull(exception);
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals(MessageSource.CARD_NOT_FOUND.getText(), exception.getMessage());
    }

    @Test
    void findById_returnCard() {
        // given
        Card card = Instancio.create(Card.class);
        when(cardRepository.findById(card.getId())).thenReturn(Optional.of(card));
        // when
        Card result = underTest.findById(card.getId());
        //then
        assertEquals(card, result);
    }

    @Test
    void deactivateCard_deactivate() {
        //given
        Card card = Instancio.of(Card.class)
                .set(field(Card::getActive), true)
                .create();
        //when
        underTest.deactivateCard(card);
        //then
        assertFalse(card.getActive());
    }

    @Test
    void addOrderToCard_addOrder() {
        //given
        Card card = Instancio.create(Card.class);
        Order order = Instancio.create(Order.class);
        when(cardRepository.save(any(Card.class))).thenReturn(card);
        //when
        underTest.addOrderToCard(card, order);
        //then
        assertTrue(card.getOrders().contains(order));
        verify(cardRepository, times(1)).save(card);
    }

    @Test
    void extendExpirationDate_throwsException_whenCardNotFound() {
        //given
        Long id = 1L;
        //when
        CustomException exception = assertThrows(CustomException.class, () -> underTest.extendExpirationDate(30, id));
        //then
        assertNotNull(exception);
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals(MessageSource.CARD_NOT_FOUND.getText(), exception.getMessage());
    }

    @Test
    void extendExpirationDate_extendDate() {
        //given
        Card card = Instancio.of(Card.class)
                .set(field(Card::getExpireDate), LocalDate.of(2025, 1, 1))
                .create();
        when(cardRepository.findById(any())).thenReturn(Optional.of(card));
        //when
        underTest.extendExpirationDate(10, 1L);
        //then
        assertEquals(LocalDate.of(2025, 1, 11), card.getExpireDate());
        verify(cardRepository, times(1)).save(card);
    }
}

