package com.propertymanagement.portal.dto;


import com.propertymanagement.portal.user.Role;
import com.propertymanagement.portal.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {

    private Long id;
    private String content;
    private User sender;
    private User receiver;
    private Role role;
    private LocalDateTime timeStamp;
}
