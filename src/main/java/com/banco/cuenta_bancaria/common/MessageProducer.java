package com.banco.cuenta_bancaria.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {

    @Autowired
    private JmsTemplate jmsTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void sendMessage(SystemMessage message) {
        try {

            String jsonMessage = objectMapper.writeValueAsString(message);

            jmsTemplate.convertAndSend("QueueAuthCustomer", jsonMessage);

            System.out.println("Mensaje enviado: " + jsonMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}