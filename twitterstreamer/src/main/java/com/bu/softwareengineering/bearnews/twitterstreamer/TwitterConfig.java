package com.bu.softwareengineering.bearnews.twitterstreamer;

import com.bu.softwareengineering.bearnews.twitterstreamer.domain.BaylorTweet;
import com.bu.softwareengineering.bearnews.twitterstreamer.service.BearFeedAPIKafkaProducer;
import com.bu.softwareengineering.bearnews.twitterstreamer.util.TweetParserUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;
import twitter4j.Status;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

import java.util.Arrays;
import java.util.List;

//@Slf4j
@Configuration
public class TwitterConfig<T> {

  @Autowired
  BearFeedAPIKafkaProducer bearFeedAPIKafkaProducer;

  @Bean
  TwitterStreamFactory twitterStreamFactory() {
    return new TwitterStreamFactory();
  }

  @Bean
  TwitterStream twitterStream(TwitterStreamFactory twitterStreamFactory) {
    return twitterStreamFactory.getInstance();
  }

  @Bean
  MessageChannel outputChannel() {
    return MessageChannels.direct().get();
  }

  @Bean
  TwitterMessageProducer twitterMessageProducer(
      TwitterStream twitterStream, MessageChannel outputChannel) {

    TwitterMessageProducer twitterMessageProducer =
        new TwitterMessageProducer(twitterStream, outputChannel);

    twitterMessageProducer.setTerms(Arrays.asList("#bearFeed", "bearFeed"));

    return twitterMessageProducer;
  }

  @Bean
  IntegrationFlow twitterFlow(MessageChannel outputChannel) {
    return IntegrationFlows.from(outputChannel)
        .transform(Status::getText)
        .handle(m -> {
          try {
            sendToBearNewsApiBackend(m.getPayload().toString());
          } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
          }
        })
        .get();
  }

  public  void sendToBearNewsApiBackend(String payload) throws JsonProcessingException {
    if(payload.length()>5){
      TweetParserUtil tweetParserUtil = new TweetParserUtil();
      BaylorTweet baylorTweet = new BaylorTweet();
      baylorTweet.setDescription(payload);
      List<String> urls = tweetParserUtil.extractUrls(payload);
      List<String> hashTags = tweetParserUtil.extractHashTag(payload);
      baylorTweet.setHashTags(hashTags);
      if(urls.size()>1){
        baylorTweet.setDetailLink(urls.get(0));
        baylorTweet.setThumbLink(urls.get(1));
      }else if (urls.size() ==1){
        baylorTweet.setDetailLink(urls.get(0));
      }
      bearFeedAPIKafkaProducer.publishToBearNewsApiBackend(baylorTweet);
    }
    //System.out.println(payload);
  }

}
