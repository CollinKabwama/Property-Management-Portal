package com.propertymanagement.portal.user;

import com.propertymanagement.portal.dto.request.ChangePasswordRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor

public class UserController {

    private final UserService service;

    @PatchMapping
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ) {
        service.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{email}")
    @CrossOrigin(origins = "http://localhost:5173")
    @PreAuthorize("hasAnyAuthority('OWNER', 'CUSTOMER', 'ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public User findByEmail(@PathVariable String email){
   return service.findByEmail(email);
    }

}