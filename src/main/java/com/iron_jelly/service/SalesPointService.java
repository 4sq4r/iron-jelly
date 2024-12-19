package com.iron_jelly.service;

import com.iron_jelly.mapper.SalesPointMapper;
import com.iron_jelly.model.dto.SalesPointDTO;
import com.iron_jelly.model.entity.SalesPoint;
import com.iron_jelly.repository.SalesPointRepository;
import com.iron_jelly.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

}
