package service;

import com.iron_jelly.exception.CustomException;
import com.iron_jelly.mapper.CardTemplateMapper;
import com.iron_jelly.model.dto.CardTemplateDTO;
import com.iron_jelly.model.entity.CardTemplate;
import com.iron_jelly.model.entity.SalesPoint;
import com.iron_jelly.repository.CardTemplateRepository;
import com.iron_jelly.security.JwtService;
import com.iron_jelly.service.CardTemplateService;
import com.iron_jelly.service.SalesPointService;
import com.iron_jelly.util.MessageSource;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CardTemplateServiceTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private CardTemplateMapper cardTemplateMapper;

    @Mock
    private SalesPointService salesPointService;

    @Mock
    private CardTemplateRepository cardTemplateRepository;

    @InjectMocks
    private CardTemplateService underTest;

    @Test
    void saveOne_saveCardTemplate() {
        //given
        CardTemplateDTO inputDTO = new CardTemplateDTO();
        CardTemplate cardTemplate = new CardTemplate();
        SalesPoint salesPoint = new SalesPoint();
        salesPoint.setCardTemplates(new HashSet<>());
        CardTemplateDTO expectedDTO = new CardTemplateDTO();
        when(jwtService.getUsername()).thenReturn("testUser");
        when(cardTemplateMapper.toEntity(inputDTO)).thenReturn(cardTemplate);
        when(salesPointService.findEntityByExternalId(any())).thenReturn(salesPoint);
        when(cardTemplateRepository.save(cardTemplate)).thenReturn(cardTemplate);
        when(cardTemplateMapper.toDTO(cardTemplate)).thenReturn(expectedDTO);
        //when
        CardTemplateDTO resultDTO = underTest.saveOne(inputDTO);
        //then
        assertNotNull(resultDTO);
        assertEquals(expectedDTO, resultDTO);
        assertEquals("testUser", cardTemplate.getCreatedBy());
        assertEquals("testUser", cardTemplate.getUpdatedBy());
        assertTrue(cardTemplate.getActive());
        assertTrue(salesPoint.getCardTemplates().contains(cardTemplate));
    }

    @Test
    void getOne_throwsException_whenCardTemplateNotFound() {
        //given
        UUID id = UUID.randomUUID();
        when(cardTemplateRepository.findByExternalId(id)).thenReturn(Optional.empty());
        //when
        CustomException exception = assertThrows(CustomException.class, () -> underTest.getOne(id));
        //then
        assertNotNull(exception);
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals(MessageSource.CARD_TEMPLATE_NOT_FOUND.getText(), exception.getMessage());
    }

    @Test
    void deleteOne_throwsException_whenCardTemplateNotFound() {
        //given
        UUID id = UUID.randomUUID();
        when(cardTemplateRepository.findByExternalId(id)).thenReturn(Optional.empty());
        //when
        CustomException exception = assertThrows(CustomException.class, () -> underTest.deleteOne(id));
        //then
        assertNotNull(exception);
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals(MessageSource.CARD_TEMPLATE_NOT_FOUND.getText(), exception.getMessage());
    }

    @Test
    void deleteOne_deleteCardTemplate() {
        //given
        CardTemplate cardTemplate = Instancio.create(CardTemplate.class);
        when(cardTemplateRepository.findByExternalId(cardTemplate.getExternalId())).thenReturn(Optional.of(cardTemplate));
        //when
        underTest.deleteOne(cardTemplate.getExternalId());
        //then
        verify(cardTemplateRepository).delete(cardTemplate);
    }

    @Test
    void findByExternalId_throwsException_whenCardTemplateNotFound() {
        //given
        UUID id = UUID.randomUUID();
        when(cardTemplateRepository.findByExternalId(id)).thenReturn(Optional.empty());
        //when
        CustomException exception = assertThrows(CustomException.class, () -> underTest.findByExternalId(id));
        //then
        assertNotNull(exception);
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals(MessageSource.CARD_TEMPLATE_NOT_FOUND.getText(), exception.getMessage());
    }

    @Test
    void deactivateCardTemplate_throwsException_whenCardTemplateNotFound() {
        //given
        UUID id = UUID.randomUUID();
        when(cardTemplateRepository.findByExternalId(id)).thenReturn(Optional.empty());
        //when
        CustomException exception = assertThrows(CustomException.class, () -> underTest.deactivateCardTemplate(id));
        //then
        assertNotNull(exception);
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals(MessageSource.CARD_TEMPLATE_NOT_FOUND.getText(), exception.getMessage());
    }

    @Test
    void deactivateCardTemplate_deactivateCard() {
        //given
        CardTemplate cardTemplate = Instancio.create(CardTemplate.class);
        when(cardTemplateRepository.findByExternalId(cardTemplate.getExternalId())).thenReturn(Optional.of(cardTemplate));
        //when
        underTest.deactivateCardTemplate(cardTemplate.getExternalId());
        //then
        assertFalse(cardTemplate.getActive());
        verify(cardTemplateRepository).save(cardTemplate);
    }

}
