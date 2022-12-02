package com.baylor.se.project.bearnews.Controller;

import com.baylor.se.project.bearnews.Controller.dto.CommentDto;
import com.baylor.se.project.bearnews.Models.Comment;
import com.baylor.se.project.bearnews.Models.Tag;
import com.baylor.se.project.bearnews.Models.Users;
import com.baylor.se.project.bearnews.Service.CommentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    @Autowired
    CommentService commentService;

    @RequestMapping(value = "/insertComment", method = RequestMethod.POST)
    public ResponseEntity<?> insertComment(@RequestBody CommentDto commentSent,
                                           @RequestParam(name="articleId" , required = true) Long articleId) throws JsonProcessingException{

        ServiceResponseHelper serviceResponseHelper =commentService.createComment(commentSent, articleId);
        ObjectMapper objectMapper = new ObjectMapper();
        if(serviceResponseHelper.getHasError()){
            return new ResponseEntity<>(objectMapper.writeValueAsString(serviceResponseHelper),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(objectMapper.writeValueAsString(serviceResponseHelper),HttpStatus.CREATED);
    }
    @RequestMapping(value = "/findAllComments", method = RequestMethod.GET)
    public ResponseEntity<?> getAllComments(){
        List<Comment> foundComments = commentService.findAllComments();
        return new ResponseEntity<>(foundComments,HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteComment", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteComment( @RequestParam(name="commentId" , required = true) Long commentId) throws JsonProcessingException {
        commentService.deleteComment(commentId);
        return new ResponseEntity(HttpStatus.OK);

    }
}
