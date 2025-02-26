package service;

import com.iron_jelly.exception.CustomException;
import com.iron_jelly.mapper.CompanyMapper;
import com.iron_jelly.model.dto.CompanyDTO;
import com.iron_jelly.model.entity.Company;
import com.iron_jelly.model.entity.User;
import com.iron_jelly.repository.CompanyRepository;
import com.iron_jelly.security.JwtService;
import com.iron_jelly.service.CompanyService;
import com.iron_jelly.service.UserService;
import com.iron_jelly.util.MessageSource;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.UUID;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {

    @Spy
    private CompanyMapper companyMapper;

    @InjectMocks
    CompanyService underTest;

    @Mock
    private UserService userService;

    @Mock
    private JwtService jwtService;

    @Mock
    private CompanyRepository companyRepository;


    @Test
    void saveOne_throwsException_whenNameLengthMoreThen_30() {
        //given
        CompanyDTO companyDTO = Instancio.of(CompanyDTO.class)
                .set(field(CompanyDTO::getName), "testNameTestNameTestNameTestNameTestNameTestName")
                .create();
        when(jwtService.getUsername()).thenReturn("testName");
        //when
        CustomException exception = assertThrows(CustomException.class, () -> underTest.saveOne(companyDTO));
        //then
        assertNotNull(exception);
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals(MessageSource.COMPANY_NAME_TOO_LONG.getText(companyDTO.getName()), exception.getMessage());
    }

    @Test
    void saveOne_saveCompany() {
        //given
        CompanyDTO companyDTO = Instancio.create(CompanyDTO.class);
        Company company = Instancio.create(Company.class);
        User user = Instancio.create(User.class);
        when(jwtService.getUsername()).thenReturn(user.getEmail());
        when(companyMapper.toEntity(companyDTO)).thenReturn(company);
        when(userService.assignAdminRole(any())).thenReturn(user);
        when(companyMapper.toDTO(company)).thenReturn(companyDTO);
        when(companyRepository.save(company)).thenReturn(company);
        //when
        CompanyDTO resultDTO = underTest.saveOne(companyDTO);
        //then
        verify(companyRepository).save(company);
        assertEquals(companyDTO, resultDTO);
    }

    @Test
    void getOne_throwsException_whenCompanyNotFound() {
        //given
        UUID id = UUID.randomUUID();
        //when
        CustomException exception = assertThrows(CustomException.class, () -> underTest.getOne(id));
        //then
        assertNotNull(exception);
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals(MessageSource.COMPANY_NOT_FOUND.getText(id.toString()), exception.getMessage());
    }

    @Test
    void updateOne_throwsException_whenCompanyNotFound() {
        //given
        UUID id = UUID.randomUUID();
        CompanyDTO companyDTO = Instancio.create(CompanyDTO.class);
        //when
        CustomException exception = assertThrows(CustomException.class, () -> underTest.updateOne(id, companyDTO));
        //then
        assertNotNull(exception);
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals(MessageSource.COMPANY_NOT_FOUND.getText(id.toString()), exception.getMessage());
    }

    @Test
    void updateOne_updateCompany() {
        //given
        CompanyDTO companyDTO = Instancio.create(CompanyDTO.class);
        UUID id = companyDTO.getExternalId();
        Company company = Instancio.create(Company.class);
        when(companyRepository.findByExternalId(id)).thenReturn(Optional.of(company));
        //when
        underTest.updateOne(id, companyDTO);
        //then
        assertEquals(companyDTO.getName(), company.getName());
        verify(companyRepository).save(company);
    }

    @Test
    void deleteOne_throwsException_whenCompanyNotFound() {
        //given
        UUID id = UUID.randomUUID();
        //when
        CustomException exception = assertThrows(CustomException.class, () -> underTest.deleteOne(id));
        //then
        assertNotNull(exception);
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals(MessageSource.COMPANY_NOT_FOUND.getText(id.toString()), exception.getMessage());
    }

    @Test
    void deleteOne_deleteCompany() {
        //given
        Company company = Instancio.create(Company.class);
        UUID id = company.getExternalId();
        when(companyRepository.findByExternalId(id)).thenReturn(Optional.of(company));
        //when
        underTest.deleteOne(id);
        //then
        verify(companyRepository).delete(company);
    }

    @Test
    void findEntityByExternalId_throwsException_whenCompanyNotFound() {
        //given
        UUID id = UUID.randomUUID();
        when(companyRepository.findByExternalId(id)).thenReturn(Optional.empty());
        //when
        CustomException exception = assertThrows(CustomException.class, () -> underTest.findEntityByExternalId(id));
        //then
        assertNotNull(exception);
        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals(MessageSource.COMPANY_NOT_FOUND.getText(id.toString()), exception.getMessage());
    }

    @Test
    void findEntityByExternalId_returnCompany() {
        //given
        Company company = Instancio.create(Company.class);
        UUID id = company.getExternalId();
        when(companyRepository.findByExternalId(id)).thenReturn(Optional.of(company));
        //when
        Company resultCompany = underTest.findEntityByExternalId(id);
        //then
        assertEquals(company, resultCompany);
    }

}
