package com.baylor.se.project.bearnews.Service;

import com.baylor.se.project.bearnews.Models.Tag;
import com.baylor.se.project.bearnews.Models.Users;
import com.baylor.se.project.bearnews.Repository.TagRepository;
import com.baylor.se.project.bearnews.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UsersService {

    @Autowired
    UsersRepository userRepo;

    @Autowired
    TagService tagService;

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

    public Users interestListAttach(List<String> interestLists){
        Users usersToUpdate = new Users();
        List<Tag> tagsToAttach = tagService.ListOfTagsFound(interestLists);
        Optional<Users> userQueryOpt = userRepo.findById(5L);
        if(userQueryOpt.isPresent()){
            usersToUpdate = userQueryOpt.get();
            usersToUpdate.setIsLiked(tagsToAttach);
            userRepo.save(usersToUpdate);
        }
        return usersToUpdate;

    }

}
