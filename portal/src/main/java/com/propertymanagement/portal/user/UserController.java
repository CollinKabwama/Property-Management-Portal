package com.propertymanagement.portal.user;

import com.propertymanagement.portal.dto.request.ChangePasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PreAuthorize("hasAnyAuthority('OWNER', 'CUSTOMER', 'ADMIN')")
    @PatchMapping
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ) {
        service.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyAuthority('OWNER', 'CUSTOMER', 'ADMIN')")
    @GetMapping("/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email){
        return ResponseEntity.ok(service.getUserByEmail(email));
    }
}