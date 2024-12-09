package com.iron_jelly.service;

import com.iron_jelly.exception.CustomException;
import com.iron_jelly.mapper.CompanyMapper;
import com.iron_jelly.model.dto.CompanyDTO;
import com.iron_jelly.model.entity.Company;
import com.iron_jelly.repository.CompanyRepository;
import com.iron_jelly.util.MessageSource;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Transactional(rollbackFor = Exception.class)
    public CompanyDTO saveOne(CompanyDTO companyDTO) {
        String name = companyDTO.getName().trim();
        validateName(name);
        companyDTO.setName(name);
        Company company = companyMapper.toEntity(companyDTO);
        companyRepository.save(company);

        return companyMapper.toDTO(company);
    }

    public CompanyDTO getOne(UUID id) {
        return companyMapper.toDTO(findEntityByExternalId(id));
    }

    @Transactional(rollbackFor = Exception.class)
    public CompanyDTO updateOne(UUID id, @Valid CompanyDTO companyDTO) {
        Company company = findEntityByExternalId(id);
        String name = companyDTO.getName().trim();
        validateName(name);
        company.setName(name);
        companyRepository.save(company);

        return companyMapper.toDTO(company);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteOne(UUID id) {
        Company company = findEntityByExternalId(id);
        companyRepository.delete(company);
    }

    public Company findEntityByExternalId(UUID id) {
        return companyRepository.findByExternalId(id).orElseThrow(
                () -> CustomException.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message(MessageSource.COMPANY_NOT_FOUND.getText(id.toString()))
                        .build());
    }

    private void validateName(String name) {
        if (name.length() > 30) {
            throw CustomException.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message(MessageSource.COMPANY_NAME_TOO_LONG.getText(name))
                    .build();
        }
    }
}
