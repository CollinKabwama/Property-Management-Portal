package com.propertymanagement.portal.service.impl;

import com.propertymanagement.portal.domain.Customer;
import com.propertymanagement.portal.domain.Message;
import com.propertymanagement.portal.domain.Owner;
import com.propertymanagement.portal.dto.MessageDTO;
import com.propertymanagement.portal.repository.CustomerRepository;
import com.propertymanagement.portal.repository.MessageRepository;
import com.propertymanagement.portal.repository.OwnerRepository;
import com.propertymanagement.portal.service.MessageService;
import com.propertymanagement.portal.user.User;
import com.propertymanagement.portal.user.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class MessageServiceImp implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired

    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public MessageDTO createMessageAndSend(MessageDTO messageDTO) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUserEmail = authentication.getName();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        User sender;
        User receiver;

        boolean isCustomer = authorities.stream().anyMatch(a -> a.getAuthority().equals("CUSTOMER"));
        boolean isOwner = authorities.stream().anyMatch(a -> a.getAuthority().equals("OWNER"));

        if (isCustomer) {
            Customer customer = customerRepository.findCustomerByUserEmail(loggedInUserEmail);
            sender = customer.getUser();
        } else if (isOwner) {
            Owner owner = ownerRepository.findOwnerByUserEmail(loggedInUserEmail);
            sender = owner.getUser();
        } else {
            throw new IllegalArgumentException("Authenticated user is not a Customer or an Owner");
        }

       receiver = switch (messageDTO.getReceiver().getRole()) {
            case CUSTOMER -> customerRepository.findById(Long.valueOf(messageDTO.getReceiver().getId()))
                    .orElseThrow(() -> new NoSuchElementException("Customer receiver not found")).getUser();
            case OWNER -> ownerRepository.findById(Long.valueOf(messageDTO.getReceiver().getId()))
                    .orElseThrow(() -> new NoSuchElementException("Owner receiver not found")).getUser();
            default -> throw new IllegalArgumentException("Unknown Receiver Role");
        };

        Message message = new Message();
            message.setSender(sender);
            message.setReceiver(receiver);
            message.setContent(messageDTO.getContent());
            message.setTimeStamp(LocalDateTime.now());

            return modelMapper.map(messageRepository.save(message), MessageDTO.class);

    }

    @Override
    public List<MessageDTO> getAllMessages() {

        List<Message> messages = messageRepository.findAll();
        return messages.stream()
                .map(message -> modelMapper.map(message, MessageDTO.class))
                .collect(Collectors.toList());

    }

    public MessageDTO convertToDTO(Message message) {

        return modelMapper.map(message, MessageDTO.class);
    }

    public Message convertToEntity(MessageDTO messageDTO) {

        return modelMapper.map(messageDTO, Message.class);
    }


}
