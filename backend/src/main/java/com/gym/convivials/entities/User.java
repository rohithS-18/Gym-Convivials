package com.gym.convivials.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

import java.util.Date;

@jakarta.persistence.Entity
@Table(name="users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    private int userId;
    private String username;
    private String email;
    private String pasword;
    private String city;
    @Column(name = "dob")
    private Date DOB;
    private String gender;
    @Column(columnDefinition = "geography(Point,4326)")
    private Point location;
    @Column(name = "profilepic")
    private String profilePic;
}
