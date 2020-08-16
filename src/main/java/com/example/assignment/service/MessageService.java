package com.example.assignment.service;

import com.example.assignment.Repository.MessageRepository;
import com.example.assignment.exception.ResourceNotFoundException;
import com.example.assignment.model.MQMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    Environment environment;
    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    public MQMessage getMessageById(String id) throws ResourceNotFoundException {
        logger.info("----Getting  MQMessage from  MongoDb----");
        return messageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found for this id :: " + id));
    }

    public MQMessage updateMQMessage(String id, MQMessage mqMessage) throws ResourceNotFoundException {
        logger.info("----updating MQMessage in MongoDb----");
        MQMessage message = messageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Message not found for this id :: " + id));
        mqMessage.setStatus(message.getStatus());
        messageRepository.save(mqMessage);
        logger.info("----updated MQMessage in Mongodb----");
        forwardMQMessage(mqMessage);
        return mqMessage;
    }
    public void forwardMQMessage(MQMessage mqMessage)
    {

        jmsTemplate.convertAndSend(environment.getProperty("MESSAGE_OUTPUT_QUEUE"), mqMessage);
        logger.info("----Message has been sent to OUTPUT.QUEUE----");
    }





     public List<MQMessage> getAllMessage() {
    return messageRepository.findAll();
    }
}


