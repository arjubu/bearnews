package com.baylor.se.project.bearnews.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    //private Users email;

    @Column
    private boolean isActive;

    @Column
    private String profileImageUrl;

    @Column
    private String socialMediaLink;


}
