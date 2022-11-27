package com.baylor.se.project.bearnews.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDetails {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String username;

    //@OneToOne(mappedBy = "username")
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "loginDetails_email", referencedColumnName = "email")
    private String email;

    @Column
    private String password;
}
