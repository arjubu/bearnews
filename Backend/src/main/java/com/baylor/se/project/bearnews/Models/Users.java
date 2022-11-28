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


    //@OneToMany(mappedBy = "users")
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "USER_TAG",
            joinColumns = {@JoinColumn(name = "USER_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "TAG_ID", referencedColumnName = "ID")})
    private List<Tag> isLiked;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @OneToMany(mappedBy = "createdBy",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Article> articles;


}
