package com.banco.cuenta_bancaria.controller.publisher;

import com.banco.cuenta_bancaria.common.SystemMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublishController {

    @Autowired
    private JmsTemplate jmsTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/publishMessage")
    public ResponseEntity<String> publishMessage(@RequestBody SystemMessage systemMessage){
        try {
            String jsonMessage = objectMapper.writeValueAsString(systemMessage);

            jmsTemplate.convertAndSend("QueueAuthCustomer", jsonMessage);

            return new ResponseEntity<>("Mensaje enviado con éxito", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
