package com.propertymanagement.portal.user;

import com.propertymanagement.portal.dto.request.ChangePasswordRequest;
//import com.propertymanagement.portal.email.EmailService;
import com.propertymanagement.portal.email.EmailService;
import com.propertymanagement.portal.exception.InvalidInputException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final EmailService emailService;

    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new InvalidInputException("Wrong password");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new InvalidInputException("Password are not the same");
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        emailService.sendEmail(user.getEmail(), "Password reset", "Hello, You have successfully changed your password. If you did not make this change, please contact us immediately.");


        // save the new password
        repository.save(user);
    }
    public User getUserByEmail(String email){
        return repository.findByEmail( email).orElseThrow(()-> new InvalidInputException("User not found"));
    }
}
