package com.baylor.se.project.bearnews.Service;

import com.baylor.se.project.bearnews.Model.User;
import com.baylor.se.project.bearnews.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public static boolean patternMatches(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }
    public String RegisterUserToSystem(User user){
        User newUser = new User();
        String regexPattern = "^(.+)@(\\S+)$";
        if((patternMatches(regexPattern,user.getEmail()))==false)
            return "email pattern is invalid";
        else{
            userRepository.save(newUser);
            return String.valueOf(newUser.getId());
        }
    }

    public List<User> listAllUser(){
        List<User> allUserRegistered = userRepository.findAll();
        return allUserRegistered;
    }
}
