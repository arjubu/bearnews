package com.baylor.se.project.bearnews.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
}
