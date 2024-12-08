//package com.iron_jelly.controller;
//
//import com.iron_jelly.model.dto.AdminDTO;
//import com.iron_jelly.model.entity.User;
//import com.iron_jelly.service.AdminService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@Slf4j
//@RestController
//@RequestMapping("/users/v1")
//@RequiredArgsConstructor
//public class AdminController {
//
//    private final AdminService adminService;
//
//    @PostMapping
//    public AdminDTO saveOne(@RequestBody @Valid AdminDTO adminDTO) {
//        return adminService.createAdmin(adminDTO);
//    }
//}
