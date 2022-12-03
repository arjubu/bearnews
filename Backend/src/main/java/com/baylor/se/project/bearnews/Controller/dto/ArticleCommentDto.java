package com.baylor.se.project.bearnews.Controller.dto;

import com.baylor.se.project.bearnews.Models.ArticleType;
import com.baylor.se.project.bearnews.Models.Comment;
import com.baylor.se.project.bearnews.Models.Tag;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

public class ArticleCommentDto {
    long id;
    String title;

    String content;

    Tag contains;

    String detailLink;

    String thumbLink;

    ArticleType articleType;

    Integer baylorNewsId;

    LocalDateTime createdAt = LocalDateTime.now();

    LocalDateTime updatedAt;

    List<CommentUserDto> commentUsers;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Tag getContains() {
        return contains;
    }

    public void setContains(Tag contains) {
        this.contains = contains;
    }

    public String getDetailLink() {
        return detailLink;
    }

    public void setDetailLink(String detailLink) {
        this.detailLink = detailLink;
    }

    public String getThumbLink() {
        return thumbLink;
    }

    public void setThumbLink(String thumbLink) {
        this.thumbLink = thumbLink;
    }

    public ArticleType getArticleType() {
        return articleType;
    }

    public void setArticleType(ArticleType articleType) {
        this.articleType = articleType;
    }

    public Integer getBaylorNewsId() {
        return baylorNewsId;
    }

    public void setBaylorNewsId(Integer baylorNewsId) {
        this.baylorNewsId = baylorNewsId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<CommentUserDto> getCommentUsers() {
        return commentUsers;
    }

    public void setCommentUsers(List<CommentUserDto> commentUsers) {
        this.commentUsers = commentUsers;
    }
}
