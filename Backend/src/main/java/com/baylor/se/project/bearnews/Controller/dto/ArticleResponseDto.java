package com.baylor.se.project.bearnews.Controller.dto;

import java.time.LocalDateTime;
public class ArticleResponseDto {
    public long idOfArticle;
    public long idOfCreator;
    public long idOfTag;
    public String textOfTag;
    public String firstNameofCreator;

    public String lastNameofCreator;
    public String titleOfArticle;
    public String contentOfArticle;
    public LocalDateTime timeOfArticleCretion;

    public long getIdOfArticle() {
        return idOfArticle;
    }

    public void setIdOfArticle(long idOfArticle) {
        this.idOfArticle = idOfArticle;
    }

    public long getIdOfCreator() {
        return idOfCreator;
    }

    public void setIdOfCreator(long idOfCreator) {
        this.idOfCreator = idOfCreator;
    }

    public long getIdOfTag() {
        return idOfTag;
    }

    public void setIdOfTag(long idOfTag) {
        this.idOfTag = idOfTag;
    }

    public String getTextOfTag() {
        return textOfTag;
    }

    public void setTextOfTag(String textOfTag) {
        this.textOfTag = textOfTag;
    }

    public String getFirstNameofCreator() {
        return firstNameofCreator;
    }

    public void setFirstNameofCreator(String firstNameofCreator) {
        this.firstNameofCreator = firstNameofCreator;
    }

    public String getLastNameofCreator() {
        return lastNameofCreator;
    }

    public void setLastNameofCreator(String lastNameofCreator) {
        this.lastNameofCreator = lastNameofCreator;
    }

    public String getTitleOfArticle() {
        return titleOfArticle;
    }

    public void setTitleOfArticle(String titleOfArticle) {
        this.titleOfArticle = titleOfArticle;
    }

    public String getContentOfArticle() {
        return contentOfArticle;
    }

    public void setContentOfArticle(String contentOfArticle) {
        this.contentOfArticle = contentOfArticle;
    }

    public LocalDateTime getTimeOfArticleCretion() {
        return timeOfArticleCretion;
    }

    public void setTimeOfArticleCretion(LocalDateTime timeOfArticleCretion) {
        this.timeOfArticleCretion = timeOfArticleCretion;
    }
}
