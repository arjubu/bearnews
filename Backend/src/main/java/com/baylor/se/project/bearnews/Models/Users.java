package com.baylor.se.project.bearnews.Models;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Users {

    @Id
    @GeneratedValue
    private long id;


    @Column
    @NotNull
    private String firstName;

    @Column
    @NotNull
    private String lastName;

    @Column
    @NotNull
    private String email;

    @Column
    @NotNull
    private String password;

    @Column
    private boolean isActive = false;

    @Column
    private String profileImageUrl;

    @Column
    private String socialMediaLink;



    /*@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "users_id")*/
    @OneToMany
    private List<Tag> isLiked;

    @Enumerated(EnumType.STRING)
    private UserType userType;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "USERS_ID", referencedColumnName = "id")
    private List<Article> articles = new ArrayList<>();

    @OneToMany(mappedBy = "createdByevent",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> events;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @Column
    private Long likedArticleId;


}
