package com.baylor.se.project.bearnews.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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


    @OneToMany(mappedBy = "user") // inverse-side
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Event> events = new HashSet<>();
}
