package com.baylor.se.project.bearnews.Service;

import com.baylor.se.project.bearnews.Models.Users;
import com.baylor.se.project.bearnews.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class UsersService {

    @Autowired
    UsersRepository userRepo;

    public static boolean patternMatches(String emailAddress, String regexPattern) {
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }
    public String RegisterUserToSystem(Users user){
        Users newUser = new Users();
        String regexPattern = "^(.+)@(\\S+)$";
        if((patternMatches(regexPattern,user.getEmail()))==false)
            return "email pattern is invalid";
        else{
            userRepo.save(newUser);
            return String.valueOf(newUser.getId());
        }
    }

    public List<Users> listAllUser(){
        List<Users> allUserRegistered = userRepo.findAll();
        return allUserRegistered;
    }
}
