package com.iron_jelly.service;

import com.iron_jelly.exception.CustomException;
import com.iron_jelly.mapper.CompanyMapper;
import com.iron_jelly.model.dto.CompanyDTO;
import com.iron_jelly.model.entity.Company;
import com.iron_jelly.repository.CompanyRepository;
import com.iron_jelly.util.MessageSource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    public CompanyDTO saveOne(CompanyDTO companyDTO) {
        Company company = companyMapper.toEntity(companyDTO);
        company.setName(company.getName());

        return companyMapper.toDTO(company);
    }

    public CompanyDTO getOne(Long id) {
        return companyMapper.toDTO(findById(id));
    }

    public void deleteOne(Long id) {
        Company company = findById(id);
        companyRepository.delete(company);
    }

    public Company findById(Long id) {
        return companyRepository.findById(id).orElseThrow(
                () -> CustomException.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message(MessageSource.COMPANY_NOT_FOUND.getText())
                        .build());
    }
}
