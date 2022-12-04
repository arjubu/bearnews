package com.baylor.se.project.bearnews.Models;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Article {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String title;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @ManyToOne
    private Tag contains;

    @Column
    private String detailLink;

    @Column
    private String thumbLink;

    @Column
    private ArticleType articleType = ArticleType.SYSTEM;

    @Column
    private Integer baylorNewsId;

    @Column
    private Integer likecount;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "users_articles",referencedColumnName = "id")
//    @JsonBackReference
    // private Users createdBy;

    @CreatedDate
    @Column
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt = LocalDateTime.now();

    @LastModifiedDate
    @Column
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updatedAt =LocalDateTime.now();


//    @OneToMany(mappedBy = "articlecomments",cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Comment> articlecomments;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> articleComments;



}
