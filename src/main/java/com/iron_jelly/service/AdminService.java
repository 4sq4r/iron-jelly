package com.iron_jelly.service;

import com.iron_jelly.mapper.AdminMapper;
import com.iron_jelly.model.entity.Admin;
import com.iron_jelly.model.entity.User;
import com.iron_jelly.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;

    public Admin createAdmin(User user) {
        Admin admin = new Admin();
        admin.setUsername(user.getUsername());
        admin.setPassword(user.getPassword());

        return adminRepository.save(admin);
    }
}
