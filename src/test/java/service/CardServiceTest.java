package service;

import com.iron_jelly.exception.CustomException;
import com.iron_jelly.mapper.CardMapper;
import com.iron_jelly.model.dto.CardDTO;
import com.iron_jelly.model.entity.Card;
import com.iron_jelly.model.entity.CardTemplate;
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
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {
    private static final String USERNAME = "username";

    @Mock
    private CardMapper cardMapper;
    @Mock
    private CardTemplateService cardTemplateService;
    @Mock
    private JwtService jwtService;
    @Mock
    private UserService userService;
    @Mock
    private CardRepository cardRepository;
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
        CardDTO cardDTO = Instancio.create(CardDTO.class);
        cardDTO.setUserId(user.getExternalId());
        CardTemplate cardTemplate = Instancio.create(CardTemplate.class);
        cardTemplate.setActive(true);
        when(cardTemplateService.findByExternalId(cardDTO.getCardTemplateId())).thenReturn(cardTemplate);
        when(userService.findEntityByExternalId(cardDTO.getUserId())).thenReturn(user);
        when(cardMapper.toEntity(cardDTO)).thenReturn(new Card());
        when(cardRepository.save(any())).thenReturn(card);
        //when
        CardDTO result = underTest.saveOne(cardDTO);
        //then
        verify(cardRepository).save(argumentCaptor.capture());
        Card savedCard = argumentCaptor.getValue();
        assertNotNull(result);
        assertEquals(cardDTO.getActive(), savedCard.getActive());
    }
}

