package com.baylor.se.project.bearnews.Controller;

import com.baylor.se.project.bearnews.Models.Comment;
import com.baylor.se.project.bearnews.Models.Tag;
import com.baylor.se.project.bearnews.Models.Users;
import com.baylor.se.project.bearnews.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommentController {

    @Autowired
    CommentService commentService;

    @RequestMapping(value = "/insertComment", method = RequestMethod.POST)
    public ResponseEntity<?> insertComment(@RequestBody Comment commentSent) {
      String responseReturned =commentService.createComment(commentSent);
        int intValue;
        try {
            intValue = Integer.parseInt(responseReturned);
            return new ResponseEntity<>(responseReturned,HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(responseReturned,HttpStatus.BAD_REQUEST);
        }
    }
    @RequestMapping(value = "/findAllComments", method = RequestMethod.GET)
    public ResponseEntity<?> getAllComments(){
        List<Comment> foundComments = commentService.findAllComments();
        return new ResponseEntity<>(foundComments,HttpStatus.OK);
    }
}
