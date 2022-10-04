package com.guavapay.authservice.service;

import com.guavapay.authservice.dto.request.SignInRequest;
import com.guavapay.authservice.dto.request.SignupRequest;
import com.guavapay.authservice.dto.response.JwtResponse;
import com.guavapay.authservice.dto.response.MessageResponse;

public interface AccountService {
    JwtResponse signIn(SignInRequest loginRequest);

    MessageResponse signUp(SignupRequest signupRequest);
}
