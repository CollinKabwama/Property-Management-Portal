package com.propertymanagement.portal.auth;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.propertymanagement.portal.config.JwtService;
import com.propertymanagement.portal.domain.Customer;
import com.propertymanagement.portal.domain.Owner;
import com.propertymanagement.portal.dto.request.AuthenticationRequest;
import com.propertymanagement.portal.dto.request.RegisterRequest;
import com.propertymanagement.portal.dto.response.AuthenticationResponse;
import com.propertymanagement.portal.email.EmailService;
import com.propertymanagement.portal.email.EmailService;
import com.propertymanagement.portal.exception.RecordAlreadyExistsException;
import com.propertymanagement.portal.repository.CustomerRepository;
import com.propertymanagement.portal.repository.OwnerRepository;
import com.propertymanagement.portal.token.Token;
import com.propertymanagement.portal.token.TokenRepository;
import com.propertymanagement.portal.token.TokenType;
import com.propertymanagement.portal.user.Role;
import com.propertymanagement.portal.user.User;
import com.propertymanagement.portal.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final CustomerRepository customerRepository;
    private final OwnerRepository ownerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;
    public AuthenticationResponse registerCustomer(RegisterRequest request) {
        // check existing user
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new RecordAlreadyExistsException("This username is not available!");
        }

        Role role = Role.valueOf("CUSTOMER");
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .phoneNumber(request.getPhoneNumber())
                .build();
        var savedUser = userRepository.save(user);
        Map<String, Object> claims = Map.of("role",role);
        var jwtToken = jwtService.generateToken(claims,user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        emailService.sendEmail(user.getEmail(), "Account registration", "Hello, Your account has successfully been registered");

        Customer customer = new Customer();
        customer.setUser(user);
        customerRepository.save(customer);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse registerOwner(RegisterRequest request) {
        // check existing user
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new RecordAlreadyExistsException("This email is already taken");
        }

        Role role = Role.valueOf("OWNER");
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .role(role)
                .build();
        var savedUser = userRepository.save(user);
        Map<String, Object> claims = Map.of("role",role);
        var jwtToken = jwtService.generateToken(claims,user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        emailService.sendEmail(user.getEmail(), "Account registration", "Hello, Your account has successfully been registered. You will be notified as soon as it is activated");

        Owner owner = new Owner();
        owner.setUser(user);
        ownerRepository.save(owner);


        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse registerAdmin(RegisterRequest request) {
        // check existing user
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new RecordAlreadyExistsException("This username is not available!");
        }

        Role role = Role.valueOf("ADMIN");
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .phoneNumber(request.getPhoneNumber())
                .build();
        var savedUser = userRepository.save(user);
        Map<String, Object> claims = Map.of("role",role);
        var jwtToken = jwtService.generateToken(claims,user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);

        userRepository.save(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        Map<String, Object> claims = Map.of("role",user.getRole());
        var jwtToken = jwtService.generateToken(claims,user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.userRepository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                Map<String, Object> claims = Map.of("role",user.getRole());
                var accessToken = jwtService.generateToken(claims,user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}