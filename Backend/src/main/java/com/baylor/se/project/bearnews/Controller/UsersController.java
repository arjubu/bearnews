package com.baylor.se.project.bearnews.Controller;

import com.baylor.se.project.bearnews.Controller.dto.UserDto;
import com.baylor.se.project.bearnews.Controller.dto.UserInterestDto;
import com.baylor.se.project.bearnews.Models.Users;
import com.baylor.se.project.bearnews.ResponseObjectMappers.ArticleByUsersObjectMapper;
import com.baylor.se.project.bearnews.Service.TagService;
import com.baylor.se.project.bearnews.Service.UsersService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class UsersController {

    @Autowired
    UsersService usersService;
    @Autowired
    TagService tagService;

    @RequestMapping(path="/createUser", method = RequestMethod.POST)
    public ResponseEntity<?> registerForAccount(@RequestBody @Validated UserDto userDto) throws JsonProcessingException {
        Users users = new Users();
        users.setFirstName(userDto.getFirstName());
        users.setLastName(userDto.getLastName());
        users.setEmail(userDto.getEmail());
        users.setPassword(userDto.getPassword());
        users.setProfileImageUrl(userDto.getProfileImageUrl());
        users.setSocialMediaLink(userDto.getSocialMediaLink());
        ServiceResponseHelper serviceResponseHelper = usersService.RegisterUserToSystem(users);
        ObjectMapper objectMapper = new ObjectMapper();
        if(serviceResponseHelper.getHasError()){
            return new ResponseEntity<>(objectMapper.writeValueAsString(serviceResponseHelper),HttpStatus.BAD_REQUEST);
        }
        else {
            return new ResponseEntity<>(objectMapper.writeValueAsString(serviceResponseHelper),HttpStatus.CREATED);
        }
    }

    @RequestMapping(value = "/findAllUsers", method = RequestMethod.GET)
    public ResponseEntity<?> getAllUsers(){
        List<Users> foundApplicants = usersService.listAllUser();
        return new ResponseEntity<>(foundApplicants,HttpStatus.OK);
    }

   /* @RequestMapping(value = "/updateUserInterest", method = RequestMethod.PUT)
    public ResponseEntity<?> attachInterestList(@RequestBody Map<String, String> json,
                                                @RequestParam (name="usersId" , required = false) Long usersId) {
        Users userFound = usersService.foundUserById(usersId);
        if(userFound!=null && json.size()>=3) {
            String tg1 = json.get("tag1");
            String tg2 = json.get("tag2");
            String tg3 = json.get("tag3");

            List<String> userInterestList = new ArrayList<>();
            userInterestList.add(tg1);
            userInterestList.add(tg2);
            userInterestList.add(tg3);
            Users updateUsers = usersService.interestListAttach(userInterestList,usersId);
            return new ResponseEntity<>(updateUsers, HttpStatus.OK);
        }
        return new ResponseEntity<>("doesn't exsist", HttpStatus.BAD_REQUEST);
    }*/

    @PostMapping("/validateOtp")
    public ResponseEntity<?> validateOtp(@RequestBody Map<String,String> requestBody) throws JsonProcessingException {
        ServiceResponseHelper serviceResponseHelper = usersService.validateOtp(requestBody);
        ObjectMapper objectMapper = new ObjectMapper();
        if(serviceResponseHelper.getHasError()){
            return new ResponseEntity<>(objectMapper.writeValueAsString(serviceResponseHelper),HttpStatus.BAD_REQUEST);
        }
        else {
            return new ResponseEntity<>(objectMapper.writeValueAsString(serviceResponseHelper),HttpStatus.OK);
        }
    }
    @RequestMapping(value = "/deleteUserById", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAUser( @RequestParam (name="usersId" , required = true) Long usersId) throws JsonProcessingException{
        ServiceResponseHelper serviceResponseHelper = usersService.deleteUSer(usersId);
        ObjectMapper objectMapper = new ObjectMapper();
        if(serviceResponseHelper.getHasError()){
            return new ResponseEntity<>(objectMapper.writeValueAsString(serviceResponseHelper),HttpStatus.BAD_REQUEST);
        }
        else {
            return new ResponseEntity<>(objectMapper.writeValueAsString(serviceResponseHelper),HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/getArticlesByUsers", method = RequestMethod.GET)
    public ResponseEntity<?> articleByUsers( @RequestParam (name="usersId" , required = true) Long usersId) throws JsonProcessingException{
      List<ArticleByUsersObjectMapper> responseReturned= usersService.findArticlesByUsers(usersId);
      if(responseReturned.isEmpty()==false)
      return new ResponseEntity<>(responseReturned,HttpStatus.OK);
      else
          return new ResponseEntity<>("the user didn't have article",HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/findingAuthors", method = RequestMethod.GET)
    public ResponseEntity<?> getAllAuthors(){
        usersService.findingArticlesByUser(37L);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/userLogin")
    public ResponseEntity<?> validateLogin(@RequestBody Map<String,String> requestBody) throws JsonProcessingException {
        ServiceResponseHelper serviceResponseHelper = usersService.validateLogin(requestBody);
        ObjectMapper objectMapper = new ObjectMapper();
        if(serviceResponseHelper.getHasError()){
            return new ResponseEntity<>(objectMapper.writeValueAsString(serviceResponseHelper),HttpStatus.BAD_REQUEST);
        }
        else {
            return new ResponseEntity<>(objectMapper.writeValueAsString(serviceResponseHelper),HttpStatus.OK);
        }
    }
    @RequestMapping(value = "/displayUserProfile", method = RequestMethod.POST)
    public ResponseEntity<?> findUserProfile(@RequestBody Map<String,String> requestBody) throws JsonProcessingException{

        String userEmail = requestBody.get("username");
        ServiceResponseHelper serviceResponseHelper = usersService.findUserProfile(userEmail);

        ObjectMapper objectMapper = new ObjectMapper();
        if(serviceResponseHelper.getHasError()){
            return new ResponseEntity<>(objectMapper.writeValueAsString(serviceResponseHelper),HttpStatus.BAD_REQUEST);
        }
        else {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("data", serviceResponseHelper.getContent());
            return new ResponseEntity<>(map,HttpStatus.OK);

        }
    }


    @RequestMapping(value = "/updateUserProfile", method = RequestMethod.PUT)
    public ResponseEntity<?> updatingUserProfile(@RequestBody UserInterestDto requestBody){
        //System.out.println("calling from frontend");
        System.out.println(requestBody.getTagsContaining());
        System.out.println(requestBody.getUsername());
        usersService.interestListAttach(requestBody);
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
