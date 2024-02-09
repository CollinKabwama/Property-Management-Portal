package com.propertymanagement.portal.service;

import com.propertymanagement.portal.domain.Message;
import com.propertymanagement.portal.dto.MessageDTO;

import java.util.List;


public interface MessageService {

    public MessageDTO createMessageAndSend(MessageDTO messageDTO);

    public List <MessageDTO> getAllMessages();

    public MessageDTO convertToDTO(Message message);

    public Message convertToEntity(MessageDTO messageDTO);
}
