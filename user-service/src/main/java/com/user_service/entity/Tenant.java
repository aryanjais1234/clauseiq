package com.user_service.entity;

import com.user_service.enums.Plan;
import jakarta.persistence.*;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Entity
@Table(name = "tenants")
public class Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String companyName;

    @Enumerated(EnumType.STRING)
    private Plan plan;

    @CreationTimestamp
    private LocalDateTime createdAt;


}
