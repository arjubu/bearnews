package com.baylor.se.project.bearnews.Controller;


import com.baylor.se.project.bearnews.Model.User;
import com.baylor.se.project.bearnews.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(path="/applyForAccount", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> registerForAccount(@RequestBody User users) {
        String responseReturned = userService.RegisterUserToSystem(users);
        System.out.println(responseReturned);
        return new ResponseEntity<>(responseReturned, HttpStatus.OK);
    }

    @RequestMapping(value = "/findAllUsers", method = RequestMethod.GET)
    public ResponseEntity<?> getAllUsers(){
        List<User> foundApplicants = userService.listAllUser();
        System.out.println(foundApplicants);
        return new ResponseEntity<>(foundApplicants,HttpStatus.OK);
    }
}
