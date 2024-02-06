package com.propertymanagement.portal.demo;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo-controller")
public class DemoController {
    @GetMapping
    public ResponseEntity<String> testingHello() {
        return ResponseEntity.ok("Hello");
    }

    @PreAuthorize("hasAuthority('OWNER')")
    @GetMapping("/owner")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello from secured endpoint: OWNER");
    }

    @PreAuthorize("hasAnyAuthority('OWNER', 'CUSTOMER')")
    @GetMapping("/both")
    public ResponseEntity<String> both() {
        return ResponseEntity.ok("Hello from secured endpoint:Both");
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping("/customer")
    public ResponseEntity<String> admin() {
        return ResponseEntity.ok("Hello from secured endpoint: CUSTOMER");
    }

}

