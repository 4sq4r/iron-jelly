package com.iron_jelly.service;

import com.iron_jelly.exception.CustomException;
import com.iron_jelly.mapper.SalesPointMapper;
import com.iron_jelly.model.dto.SalesPointDTO;
import com.iron_jelly.model.entity.SalesPoint;
import com.iron_jelly.repository.SalesPointRepository;
import com.iron_jelly.security.JwtService;
import com.iron_jelly.util.MessageSource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SalesPointService {
    private final SalesPointMapper salesPointMapper;
    private final CompanyService companyService;
    private final SalesPointRepository salesPointRepository;
    private final JwtService jwtService;

    public void saveOne (SalesPointDTO salesPointDTO) {
        String username = jwtService.getUsername();

        SalesPoint salesPoint = salesPointMapper.toEntity(salesPointDTO);
        salesPoint.setCreatedBy(username);
        salesPoint.setUpdatedBy(username);
        salesPoint.setCompany(companyService.findEntityByExternalId(salesPointDTO.getCompanyId()));
        salesPointRepository.save(salesPoint);
    }

    public SalesPoint findEntityByExternalId(UUID id) {
        return salesPointRepository.findByExternalId(id).orElseThrow(
                () -> CustomException.builder()
                        .httpStatus(HttpStatus.BAD_REQUEST)
                        .message(MessageSource.SALES_POINT_NOT_FOUND.getText(id.toString()))
                        .build());
    }
}
