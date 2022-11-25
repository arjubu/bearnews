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
