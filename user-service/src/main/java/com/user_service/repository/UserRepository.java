package com.user_service.repository;

import com.user_service.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<AppUser, UUID> {
    <Optional> AppUser findByEmail(String email);
    boolean existsByEmail(String email);
}
