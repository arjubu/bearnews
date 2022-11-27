package com.baylor.se.project.bearnews.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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
    //relationship later



    @Column
    private String detailLink;

    @Column
    private String thumbLink;

    @Column
    private ArticleType articleType = ArticleType.SYSTEM;

    @Column
    private Integer baylorNewsId;
    
    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users users;

    @CreatedDate
    @Column
    private LocalDateTime createdAt = LocalDateTime.now();

    @LastModifiedDate
    @Column
    private LocalDateTime updatedAt;



}
