//package com.iron_jelly.model.entity;
//
//import jakarta.persistence.*;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//
//@Data
//@Entity
//@Table(name = "admins")
//@EqualsAndHashCode(callSuper = true)
//public class Admin extends User {
//
//    @OneToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "company_id", referencedColumnName = "id")
//    private Company companyId;
//}
