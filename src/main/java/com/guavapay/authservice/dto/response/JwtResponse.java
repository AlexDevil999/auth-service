package com.guavapay.authservice.dto.response;

import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse {
    private String id;
    private String accessToken;
    private String username;
    private Set<String> roles;
}
