package com.bu.softwareengineering.bearnews.twitterstreamer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class BearFeedAPIKafkaProducer<T> {

    @Autowired
    KafkaTemplate kafkaTemplate;

    public void publishToBearNewsApiBackend(T message) throws JsonProcessingException {
        kafkaTemplate.send("baylor-tweet-topic", new ObjectMapper().writeValueAsString(message));
    }
}
