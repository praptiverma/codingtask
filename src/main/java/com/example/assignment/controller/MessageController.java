package com.example.assignment.controller;

import com.example.assignment.exception.ResourceNotFoundException;
import com.example.assignment.model.MQMessage;
import com.example.assignment.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.JmsException;
import org.springframework.web.bind.annotation.*;
import org.springframework.jms.core.JmsTemplate;


import java.util.List;
@RestController
public class MessageController {

    @Autowired
    MessageService messageService;
    @Autowired
    JmsTemplate jmsTemplate;
    @Autowired
    Environment environment;
    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @PostMapping("/message")
    public ResponseEntity<String> publish(@RequestBody MQMessage message )throws JmsException,ResourceNotFoundException {
        try {
            jmsTemplate.convertAndSend(environment.getProperty("MESSAGE_INPUT_QUEUE"), message);
            message.setStatus("received");

            logger.info("----posting new  MQMessage into  mongodb----");
            return new ResponseEntity(message, HttpStatus.OK);
        }
        catch(JmsException e)
        {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }


    @PutMapping("/updateMQMessage/{id}")
    public MQMessage updateMQMessage( @PathVariable(value = "id") String id,
                                      @RequestBody MQMessage message) throws ResourceNotFoundException {
        logger.info("----Getting MQMessage into  mongodb----");
        return messageService.updateMQMessage(id,message);


    }

    @GetMapping("/getAllMessage")
    public List<MQMessage> getAllMessage() {
        logger.info("----Getting MQMessages from mongodb----");
        return messageService.getAllMessage();
    }

    @GetMapping("/getMessage/{id}")
    public ResponseEntity<MQMessage> getMessageById(@PathVariable(value = "id") String id)
            throws ResourceNotFoundException {
        logger.info("----Getting MQMessage from mongodb----");
        return ResponseEntity.ok().body(messageService.getMessageById(id));

    }

}
