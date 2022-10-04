package com.guavapay.authservice.service.impl;

import com.guavapay.authservice.config.jwt.JwtTokenUtils;
import com.guavapay.authservice.config.userdetails.UserDetailsImpl;
import com.guavapay.authservice.dto.request.SignInRequest;
import com.guavapay.authservice.dto.request.SignupRequest;
import com.guavapay.authservice.dto.response.JwtResponse;
import com.guavapay.authservice.dto.response.MessageResponse;
import com.guavapay.authservice.entity.Role;
import com.guavapay.authservice.entity.User;
import com.guavapay.authservice.entity.UserInfo;
import com.guavapay.authservice.repository.RoleRepository;
import com.guavapay.authservice.repository.UserInfoRepository;
import com.guavapay.authservice.repository.UserRepository;
import com.guavapay.authservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final UserInfoRepository userInfoRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;

    private final JwtTokenUtils jwtUtils;

    @Override
    public JwtResponse signIn(SignInRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Set<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        saveRefreshToken(userDetails.getUsername());

        return JwtResponse.builder()
                .id(userDetails.getId().toString())
                .username(userDetails.getUsername())
                .accessToken(jwt)
                .roles(roles)
                .build();
    }

    @Override
    public MessageResponse signUp(SignupRequest signupRequest) {
        
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return new MessageResponse("Error: Username is already taken!");
        }

        // Create new user's account
        User user = saveAccount(signupRequest);

        if(signupRequest.getFirstname().isEmpty() && signupRequest.getLastname().isEmpty()){
            return new MessageResponse("FirstName and LastName is empty!");
        }

        saveUserInfo(signupRequest, user);

        userRepository.save(user);

        return new MessageResponse("User registered successfully!");

    }

    private void saveRefreshToken(String username){
        Optional<User> user = userRepository.findByUsername(username);
        user.ifPresent(value -> value.setRefreshToken(jwtUtils.generateRefreshToken(value)));
        userRepository.save(user.get());
    }

    private User saveAccount(SignupRequest signupRequest){
        // Create new user's account
        User saveUser = User.builder()
                .username(signupRequest.getUsername())
                .password(encoder.encode(signupRequest.getPassword()))
                .build();

        Set<Role> roles = new HashSet<>();

        if(!signupRequest.getRoles().isEmpty()){
            roles = signupRequest.getRoles()
                    .stream()
                    .map(roleRequest -> roleRepository.findById(roleRequest.getId()).get()).collect(Collectors.toSet());
        }

        saveUser.setRoles(roles);
        return saveUser;
    }

    private User saveUserInfo(SignupRequest signUpRequest,User user){
        UserInfo userInfo = UserInfo.builder()
                .age(signUpRequest.getAge())
                .firstname(signUpRequest.getFirstname())
                .lastname(signUpRequest.getLastname())
                .sex(signUpRequest.getSex())
                .build();


        user.setUserInfo(userInfoRepository.save(userInfo));
        return user;
    }
}
