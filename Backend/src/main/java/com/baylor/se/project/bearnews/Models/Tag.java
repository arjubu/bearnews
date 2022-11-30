package com.baylor.se.project.bearnews.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    @Id
    @GeneratedValue
    private long id;
    @Column
    private String tagText;

    @ManyToOne
    @JoinColumn(name = "users_id")
    @JsonIgnore
    private Users users;

//    @OneToMany(cascade=CascadeType.ALL)
//    @JoinColumn(name="ARTICLE_ID")
//    @JsonIgnore
    @OneToMany
            //(cascade = CascadeType.ALL, orphanRemoval = true)
   /* @JoinTable(name = "TAG_ARTICLE",
        joinColumns = {@JoinColumn(name = "TAG_ID", referencedColumnName = "ID")},
        inverseJoinColumns = {@JoinColumn(name = "ARTICLE_ID", referencedColumnName = "ID")})
    @JsonIgnore*/
    private List<Article> belongsArticles;
}
