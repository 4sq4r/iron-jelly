package com.iron_jelly.repository;

import com.iron_jelly.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmailIgnoreCase(String email);

    Optional<User> findByExternalId(UUID id);

    Optional<User> findByEmailIgnoreCase(String username);
}
