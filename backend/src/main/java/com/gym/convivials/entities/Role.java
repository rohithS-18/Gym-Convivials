package com.gym.convivials.entities;

import com.gym.convivials.enums.ROLES;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Role {
    @Id
    private Integer id;
    private String name;
}
