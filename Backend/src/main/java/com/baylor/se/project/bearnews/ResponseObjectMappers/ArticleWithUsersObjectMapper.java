package com.baylor.se.project.bearnews.ResponseObjectMappers;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ArticleWithUsersObjectMapper {
    public long idOfArticle;
    public long idOfCreator;
    public long idOfTag;
    public String textOfTag;
    public String nameofCreator;
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

    public String getNameofCreator() {
        return nameofCreator;
    }

    public void setNameofCreator(String nameofCreator) {
        this.nameofCreator = nameofCreator;
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
