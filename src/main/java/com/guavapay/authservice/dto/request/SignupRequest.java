package com.guavapay.authservice.dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Data
@RequiredArgsConstructor
public class SignupRequest {

    private String username;
    private String firstname;
    private String lastname;
    private String sex;
    private Integer age;
    private String password;
    private Set<RoleRequest> roles;

}
