package com.baylor.se.project.bearnews.Controller.dto;

import com.baylor.se.project.bearnews.Models.ArticleType;
import com.baylor.se.project.bearnews.Models.Tag;
import com.baylor.se.project.bearnews.Models.Users;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

public class ArticleDto {

    private String articleTitle;

    private String articleContent;


    private String tagContainingText;

    private String createdUsersEmail;


    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public String getTagContainingText() {
        return tagContainingText;
    }

    public void setTagContainingText(String tagContainingText) {
        this.tagContainingText = tagContainingText;
    }

    public String getCreatedUsersEmail() {
        return createdUsersEmail;
    }

    public void setCreatedUsersEmail(String createdUsersEmail) {
        this.createdUsersEmail = createdUsersEmail;
    }



}
