package com.baylor.se.project.bearnews.Service;


import com.baylor.se.project.bearnews.Models.Tag;
import com.baylor.se.project.bearnews.Models.UserType;
import com.baylor.se.project.bearnews.Models.Users;
import com.baylor.se.project.bearnews.Repository.UsersRepository;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Component
public class TwitterAndBaylorNewsDefaultDataCreator {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    TagService tagService;

    @EventListener(ContextRefreshedEvent.class)
    public void createTwitterAndBaylorNewsUser() {
        Optional<Users> twitterUsers = usersRepository.findByEmail("twitter@baylornews.com");
        if (!twitterUsers.isPresent()) {
            Users twitter = new Users();
            twitter.setActive(true);
            twitter.setPassword(Hashing.sha256().hashString("12345", StandardCharsets.UTF_8).toString());
            twitter.setUserType(UserType.Twitter);
            twitter.setEmail("twitter@baylornews.com");
            twitter.setLastName("User");
            twitter.setFirstName("Twitter");

            usersRepository.save(twitter);
        }

        Optional<Users> baylorNewsUsers = usersRepository.findByEmail("baylornews@baylornews.com");

        if (!baylorNewsUsers.isPresent()) {
            Users baylorNews = new Users();
            baylorNews.setActive(true);
            baylorNews.setPassword(Hashing.sha256().hashString("12345", StandardCharsets.UTF_8).toString());
            baylorNews.setUserType(UserType.BearNewsPortal);
            baylorNews.setEmail("baylornews@baylornews.com");
            baylorNews.setLastName("User");
            baylorNews.setFirstName("Baylor News");

            usersRepository.save(baylorNews);

        }
    }
    @EventListener(ContextRefreshedEvent.class)
    public void createTwitterAndBaylorTags() {

        Tag tag1 = new Tag();
        tag1.setTagText("BAYLORNEWS");
        tag1 = tagService.createTag(tag1);

        Tag tag2 = new Tag();
        tag2.setTagText("BEARFEED");
        tag2 = tagService.createTag(tag2);

    }
}
