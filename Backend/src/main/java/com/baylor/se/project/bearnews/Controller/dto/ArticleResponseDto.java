package com.baylor.se.project.bearnews.Controller.dto;

import com.baylor.se.project.bearnews.Models.ArticleType;

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
    public LocalDateTime timeOfCreation;

    public  String thumbLink;

    public String detailLink;

    public ArticleType articleType;

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

    public LocalDateTime getTimeOfCreation() {
        return timeOfCreation;
    }

    public void setTimeOfCreation(LocalDateTime timeOfCreation) {
        this.timeOfCreation = timeOfCreation;
    }

    public String getThumbLink() {
        return thumbLink;
    }

    public void setThumbLink(String thumbLink) {
        this.thumbLink = thumbLink;
    }

    public String getDetailLink() {
        return detailLink;
    }

    public void setDetailLink(String detailLink) {
        this.detailLink = detailLink;
    }

    public ArticleType getArticleType() {
        return articleType;
    }

    public void setArticleType(ArticleType articleType) {
        this.articleType = articleType;
    }
}
