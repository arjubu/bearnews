package com.baylor.se.project.bearnews.Controller.dto;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import java.time.LocalDateTime;

public class CommentDto {

    private String commentText;

    private String commentEmail;

    public String getCommentEmail() {
        return commentEmail;
    }

    public void setCommentEmail(String commentEmail) {
        this.commentEmail = commentEmail;
    }


    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

}
