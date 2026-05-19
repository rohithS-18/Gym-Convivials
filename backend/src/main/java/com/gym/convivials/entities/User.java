package com.gym.convivials.entities;

import com.gym.convivials.enums.ROLES;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles", // name of the join table
            joinColumns = @JoinColumn(name = "user_id"), // foreign key to users table
            inverseJoinColumns = @JoinColumn(name = "role_id") // foreign key to roles table
    )
    private Set<Role> roles = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "gym_id")
    private Gyms gym;
    @ManyToOne
    @JoinColumn(name="gym_group_id")
    private GymGroup group;
    @OneToOne(mappedBy = "user")
    private PartnerPreference partnerPreference;
}
