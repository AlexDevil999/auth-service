package com.guavapay.authservice.dto;

import com.guavapay.authservice.entity.Role;
import lombok.Data;

import java.util.Set;

@Data
public class JwtResponse {
    private String id;
    private String type;
    private String accessToken;
    private String username;
    private Set<Role> roles;
}
