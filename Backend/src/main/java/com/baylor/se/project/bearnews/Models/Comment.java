package com.baylor.se.project.bearnews.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue
    private long id;
    @Column
    private String text;

    @CreationTimestamp
    private LocalDateTime createdCommentAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
    //make relationship later
//    @Column
//    private long userId;
//    //make relation in here
//    @Column
//    private long articleId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "users_comments",referencedColumnName = "id")
//    @JsonBackReference
//    private Users createdBycomments;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "article_comments",referencedColumnName = "id")
//    @JsonBackReference
//    private Article articlecomments;


}
