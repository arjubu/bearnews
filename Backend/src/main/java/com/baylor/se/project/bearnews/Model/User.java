package com.baylor.se.project.bearnews.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private long id;


    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String email;
    //private Users email;

    @Column
    private boolean isActive;

    @Column
    private String profileImageUrl;

    @Column
    private String socialMediaLink;
}
