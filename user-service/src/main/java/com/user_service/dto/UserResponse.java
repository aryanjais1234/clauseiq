package com.user_service.dto;

import com.user_service.enums.Plan;
import com.user_service.enums.Role;
import lombok.Builder;

import java.util.UUID;

@Builder
public class UserResponse {

    private UUID userId;

    private UUID tenantId;

    private String companyName;

    private String email;

    private Role role;

    private Plan plan;
}
