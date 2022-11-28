package com.baylor.se.project.bearnews.ResponseObjectMappers;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ArticleByUsersObjectMapper {
    public long articleId;
    public long usersCreatorId;
    public String articleTitle;
    public String articleContent;
    public LocalDateTime articleCreationTime;
    public long articleTagId;
    public String articleTagText;


    public String getArticleTagText() {
        return articleTagText;
    }

    public void setArticleTagText(String articleTagText) {
        this.articleTagText = articleTagText;
    }



    public long getArticleId() {
        return articleId;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }

    public long getUsersCreatorId() {
        return usersCreatorId;
    }

    public void setUsersCreatorId(long usersCreatorId) {
        this.usersCreatorId = usersCreatorId;
    }

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

    public LocalDateTime getArticleCreationTime() {
        return articleCreationTime;
    }

    public void setArticleCreationTime(LocalDateTime articleCreationTime) {
        this.articleCreationTime = articleCreationTime;
    }

    public long getArticleTagId() {
        return articleTagId;
    }

    public void setArticleTagId(long articleTagId) {
        this.articleTagId = articleTagId;
    }
}
