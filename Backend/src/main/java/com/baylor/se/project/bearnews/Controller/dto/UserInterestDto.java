package com.baylor.se.project.bearnews.Controller.dto;

import java.util.Map;

public class UserInterestDto {
    private String tagsContaining;

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTagsContaining() {
        return tagsContaining;
    }

    public void setTagsContaining(String tagsContaining) {
        this.tagsContaining = tagsContaining;
    }
}
