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
        Users newUser = user;
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        if((patternMatches(user.getEmail(),regexPattern)) ==false) {
            return "email pattern is invalid";
        }
        else{
            if(user.getFirstName().equals("")){
                return "first name cannot be null";
            }
            else {
                if(user.getLastName().equals("")){
                    return "last name cannot be null";
                }
                else {
                    if(userExsistence(user.getEmail())==true){
                        return "the email already exists in the system";
                    }
                    else {
                        user.setUserType(Users.type.SystemUser);
                        userRepo.save(newUser);
                        return String.valueOf(newUser.getId());
                    }
                }
            }
        }
    }

    public List<Users> listAllUser(){
        List<Users> allUserRegistered = userRepo.findAll();
        return allUserRegistered;
    }
    public boolean userExsistence(String email){
        List<Users> userInSystem = userRepo.findByEmail(email);
        if(userInSystem.isEmpty())
            return false;
        else
            return true;
    }
}
