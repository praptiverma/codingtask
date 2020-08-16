package com.example.assignment.Repository;

import com.example.assignment.model.MQMessage;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends MongoRepository <MQMessage,String> {

}
