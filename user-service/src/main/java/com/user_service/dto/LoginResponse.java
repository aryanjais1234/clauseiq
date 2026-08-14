package com.user_service.dto;


import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class LoginResponse {

    private String accessToken;

    private String tokenType;

    private Long expiresIn;

    private UserResponse user;
}
