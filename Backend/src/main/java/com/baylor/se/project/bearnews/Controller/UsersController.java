package com.baylor.se.project.bearnews.Controller;

import com.baylor.se.project.bearnews.Models.Users;
import com.baylor.se.project.bearnews.Service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsersController {

    @Autowired
    UsersService usersService;


    @RequestMapping(path="/applyForAccount", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> registerForAccount(@RequestBody Users users) {
        String responseReturned = usersService.RegisterUserToSystem(users);
        return new ResponseEntity<>(responseReturned,HttpStatus.OK);
    }

    @RequestMapping(value = "/findAllUsers", method = RequestMethod.GET)
    public ResponseEntity<?> getAllUsers(){
        List<Users> foundApplicants = usersService.listAllUser();
        return new ResponseEntity<>(foundApplicants,HttpStatus.OK);
    }
}
