package com.baylor.se.project.bearnews.Service;

import com.baylor.se.project.bearnews.Models.Comment;
import com.baylor.se.project.bearnews.Repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    public String createComment(Comment comment){
        if(comment.getText()=="")
            return "comment body couldn't be empty";
        commentRepository.save(comment);
        return String.valueOf(comment.getId());
    }
}
