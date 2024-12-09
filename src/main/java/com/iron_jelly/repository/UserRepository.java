package com.iron_jelly.repository;

import com.iron_jelly.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsernameIgnoreCase(String name);

    Optional<User> findByExternalId(UUID id);

    Optional<User> findByUsernameIgnoreCase(String username);
}
