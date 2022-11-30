package com.baylor.se.project.bearnews.Models;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.lang.Nullable;

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

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "users_articles",referencedColumnName = "id")
//    @JsonBackReference
//    private Users createdBy;

    @CreatedDate
    @Column
    private LocalDateTime createdAt = LocalDateTime.now();

    @LastModifiedDate
    @Column
    private LocalDateTime updatedAt;


    @OneToMany(mappedBy = "articlecomments",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> articlecomments;



}
