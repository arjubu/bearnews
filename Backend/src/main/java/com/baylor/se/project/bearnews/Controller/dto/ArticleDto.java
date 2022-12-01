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


    private long tagContaiedId;

    private String createdUsername;


//    private String articleDetailLink;
//
//    private String articleThumbLink;

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

    public long getTagContaiedId() {
        return tagContaiedId;
    }

    public void setTagContaiedId(long tagContaiedId) {
        this.tagContaiedId = tagContaiedId;
    }

    public String getCreatedUsername() {
        return createdUsername;
    }

    public void setCreatedUsername(String createdUsername) {
        this.createdUsername = createdUsername;
    }

    //    public String getArticleDetailLink() {
//        return articleDetailLink;
//    }
//
//    public void setArticleDetailLink(String articleDetailLink) {
//        this.articleDetailLink = articleDetailLink;
//    }
//
//    public String getArticleThumbLink() {
//        return articleThumbLink;
//    }
//
//    public void setArticleThumbLink(String articleThumbLink) {
//        this.articleThumbLink = articleThumbLink;
//    }


}
