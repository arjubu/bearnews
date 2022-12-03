package com.baylor.se.project.bearnews.Controller.dto;

import java.time.LocalDateTime;

public class CommentUserDto {

    long id;
    String user;
    private String text;
    private LocalDateTime createdComment;
    private LocalDateTime updatedComment;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreatedComment() {
        return createdComment;
    }

    public void setCreatedComment(LocalDateTime createdComment) {
        this.createdComment = createdComment;
    }

    public LocalDateTime getUpdatedComment() {
        return updatedComment;
    }

    public void setUpdatedComment(LocalDateTime updatedComment) {
        this.updatedComment = updatedComment;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
