package com.baylor.se.project.bearnews.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    @Id
    @GeneratedValue
    private long id;


    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String email;

    @Column
    private boolean isActive;

    @Column
    private String profileImageUrl;

    @Column
    private String socialMediaLink;


    @OneToMany(mappedBy = "users")
    private List<Tag> isLiked;

    @Enumerated(EnumType.STRING)
    private type userType;
    public enum type {
        SystemUser, BearNewsPortal, Twitter;
    }


}
