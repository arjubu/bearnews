package com.baylor.se.project.bearnews.Service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BaylorCrawlersConsumerService {
    
    @Autowired
    ArticleService articleService;

    @KafkaListener(topics = "baylor-news-topic", groupId = "baylor-news-group")
    public void baylorNewsConsumer(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<Map> baylorNews = mapper.readValue(message, new TypeReference<List<Map>>(){});
        
        articleService.saveBaylorNews(baylorNews);
        System.out.println("i am here!");
    }

    @KafkaListener(topics = "baylor-tweet-topic", groupId = "baylor-tweet-group")
    public void baylorTweetConsumer(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map baylorNews = mapper.readValue(message, Map.class);

        articleService.saveBaylorTweet(baylorNews);
        System.out.println("i am here!");
    }
}
