package com.guavapay.authservice.dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SignInRequest {
    private String username;
    private String password;
}
