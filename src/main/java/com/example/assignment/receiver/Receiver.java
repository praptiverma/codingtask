package com.example.assignment.receiver;

import com.example.assignment.Repository.MessageRepository;
import com.example.assignment.controller.MessageController;
import com.example.assignment.model.MQMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jms.annotation.JmsListener;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public class Receiver {
@Autowired
    MessageController messageController;

@Autowired
    Environment environment;

@Autowired
 MessageRepository messageRepository;


    private final String MESSAGE_INPUT_QUEUE ="INPUT.QUEUE";
    private final String MESSAGE_OUTPUT_QUEUE ="OUTPUT.QUEUE";
    private final Logger logger= LoggerFactory.getLogger(this.getClass());

    @JmsListener(destination = MESSAGE_INPUT_QUEUE)
    public void receiveInputQueueMessage(MQMessage message) {

    try{

        logger.info("----Message has been received from INPUT.QUEUE----");
        message.setStatus("Waiting");
        messageRepository.save(message);
        logger.info("----Message has been saved in MongoDB");

    }
    catch(Exception e)
    {
        e.printStackTrace();
    }
    }

    @JmsListener(destination = MESSAGE_OUTPUT_QUEUE)
    public void receiveOutputQueueMessage(MQMessage message) {

        try{
            logger.info("----Received  Forward MQ message in OUTPUT.QUEUE----");
            System.out.println("Received  Forward MQ message<" + message + ">");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    }


