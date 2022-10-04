package com.guavapay.authservice.controller;


import com.guavapay.authservice.dto.request.SignInRequest;
import com.guavapay.authservice.dto.request.SignupRequest;
import com.guavapay.authservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AccountService accountService;

    @PostMapping("/sign-in")
    public ResponseEntity<?> authenticateUser(@RequestBody SignInRequest loginRequest) {

        return ResponseEntity.ok(accountService.signIn(loginRequest));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {

        return ResponseEntity.ok(accountService.signUp(signUpRequest));
    }
}