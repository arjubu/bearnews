package com.baylor.se.project.bearnews.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

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
    private long userId;
    @CreationTimestamp
    private LocalDateTime createdDateTime;



}
