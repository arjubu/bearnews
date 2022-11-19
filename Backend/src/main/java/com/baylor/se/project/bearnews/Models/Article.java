package com.baylor.se.project.bearnews.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

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
    @Column
    private String content;
    //change it to relationship later
    @Column
    private long tagId;
    //change it to relationship later
    @Column
    private long userId;
    @CreationTimestamp
    private LocalDateTime createdDateTime;



}
