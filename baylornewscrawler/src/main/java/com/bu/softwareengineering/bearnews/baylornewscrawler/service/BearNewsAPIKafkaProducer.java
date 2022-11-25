package com.bu.softwareengineering.bearnews.baylornewscrawler.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class BearNewsAPIKafkaProducer<T> {

    @Autowired
    KafkaTemplate kafkaTemplate;

    public void sendToBearNewsApiBackend(T message) throws JsonProcessingException {
        kafkaTemplate.send("baylor-news-crawler", new ObjectMapper().writeValueAsString(message));
    }
}
