package com.user_service.service;

import com.user_service.dao.TenantRepository;
import com.user_service.dao.UserRepository;
import com.user_service.dto.LoginRequest;
import com.user_service.dto.LoginResponse;
import com.user_service.dto.RegisterRequest;
import com.user_service.entity.AppUser;
import com.user_service.entity.Tenant;
import com.user_service.enums.Plan;
import com.user_service.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final TenantRepository tenantRepository;

    @Override
    public LoginResponse register(RegisterRequest registerRequest) {

        validate(registerRequest);

        Tenant tenant = createTenant(registerRequest);

        AppUser user = createUser(registerRequest, tenant);

        return buildResponse(user);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        return null;
    }

    private void validate(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

    }

    private Tenant createTenant(RegisterRequest request) {

        Tenant tenant = Tenant.builder()
                .companyName(request.getCompanyName())
                .plan(Plan.FREE)
                .build();

        return tenantRepository.save(tenant);
    }

    private AppUser createUser(RegisterRequest request,
                               Tenant tenant) {

        AppUser user = AppUser.builder()
                .tenant(tenant)
                .email(request.getEmail())
                .passwordHash(request.getPassword())
                .role(Role.ADMIN)
                .build();

        return userRepository.save(user);
    }

    private LoginResponse buildResponse(AppUser user) {

        return LoginResponse.builder()
                .accessToken(null)
                .tokenType("Bearer")
                .expiresIn(0L)
                .build();

    }
}
