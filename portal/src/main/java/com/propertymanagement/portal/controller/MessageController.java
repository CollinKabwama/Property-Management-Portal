package com.propertymanagement.portal.controller;


import com.propertymanagement.portal.dto.MessageDTO;
import com.propertymanagement.portal.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;
    @PreAuthorize("hasAuthority('CUSTOMER') || hasAuthority('OWNER')")
    @PostMapping("/send")
    public ResponseEntity<MessageDTO> createMessageAndSend(@RequestBody MessageDTO messageDTO) {
        MessageDTO createdMessage = messageService.createMessageAndSend(messageDTO);
        return ResponseEntity.ok(createdMessage);

    }
}
