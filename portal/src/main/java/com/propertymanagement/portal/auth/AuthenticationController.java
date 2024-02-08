package com.propertymanagement.portal.auth;


import com.propertymanagement.portal.dto.request.AuthenticationRequest;
import com.propertymanagement.portal.dto.request.RegisterRequest;
import com.propertymanagement.portal.dto.response.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
        @PostMapping("/register-owner")
        @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<AuthenticationResponse> registerOwner(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.registerOwner(request));
    }
    @PostMapping("/register-customer")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<AuthenticationResponse> registerManager(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.registerCustomer(request));
    }

    @PostMapping("/authenticate")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    @CrossOrigin(origins = "http://localhost:5173")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }


}
