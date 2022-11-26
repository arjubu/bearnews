package com.baylor.se.project.bearnews.Controller;

import com.baylor.se.project.bearnews.Models.Users;
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
    public ResponseEntity<?> registerForAccount(@RequestBody @Validated Users users) throws JsonProcessingException {
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

    @RequestMapping(value = "/updateUserInterest", method = RequestMethod.PUT)
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
    }

    @PostMapping("/validateOtp")
    public ResponseEntity<?> validateOtp(@RequestBody Map<String,String> requestBody) throws JsonProcessingException {
        ServiceResponseHelper serviceResponseHelper = usersService.validateOtp(requestBody);
        ObjectMapper objectMapper = new ObjectMapper();
        if(serviceResponseHelper.getHasError()){
            return new ResponseEntity<>(objectMapper.writeValueAsString(serviceResponseHelper),HttpStatus.BAD_REQUEST);
        }
        else {
            return new ResponseEntity<>(objectMapper.writeValueAsString(serviceResponseHelper),HttpStatus.CREATED);
        }
    }
}
