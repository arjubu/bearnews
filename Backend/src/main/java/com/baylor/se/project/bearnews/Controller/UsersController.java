package com.baylor.se.project.bearnews.Controller;

import com.baylor.se.project.bearnews.Models.Users;
import com.baylor.se.project.bearnews.Service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UsersController {

    @Autowired
    UsersService usersService;


    @RequestMapping(path="/applyForApplication", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> registerForAccount(@RequestBody Users users) {
        String responseReturned = usersService.RegisterUserToSystem(users);
        return new ResponseEntity<>(responseReturned,HttpStatus.OK);
    }
}
