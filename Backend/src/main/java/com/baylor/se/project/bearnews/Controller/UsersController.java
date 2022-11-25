package com.baylor.se.project.bearnews.Controller;

import com.baylor.se.project.bearnews.Models.Users;
import com.baylor.se.project.bearnews.Service.TagService;
import com.baylor.se.project.bearnews.Service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @RequestMapping(path="/applyForAccount", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> registerForAccount(@RequestBody Users users) {
        String responseReturned = usersService.RegisterUserToSystem(users);
        int intValue;
        try {
            intValue = Integer.parseInt(responseReturned);
            return new ResponseEntity<>(responseReturned,HttpStatus.OK);
        } catch (NumberFormatException e) {
            System.out.println("Input String cannot be parsed to Integer.");
        }
        return new ResponseEntity<>(responseReturned,HttpStatus.BAD_REQUEST);
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
        if(userFound!=null) {
            String tg1 = json.get("tag1");
            String tg2 = json.get("tag2");
            String tg3 = json.get("tag3");

            List<String> userInterestList = new ArrayList<>();
            userInterestList.add(tg1);
            userInterestList.add(tg2);
            userInterestList.add(tg3);
            Users updateUsers = usersService.interestListAttach(userInterestList);
            return new ResponseEntity<>(updateUsers, HttpStatus.OK);
        }
        return new ResponseEntity<>("doesn't exsist", HttpStatus.BAD_REQUEST);
    }
}
