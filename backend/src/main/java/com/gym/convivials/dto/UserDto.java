package com.gym.convivials.dto;


import com.gym.convivials.entities.Role;
import com.gym.convivials.enums.ROLES;
import lombok.Data;
import java.util.Date;
import java.util.Set;

@Data
public class UserDto {
        private Integer userId;
        private String username;
        private String email;
        private String pasword;
        private String city;
        private String profilePic;
        private Date DOB;
        private double latitude;
        private double longitude;
        private String gender;
        private Set<String> roles;
        private int gymId;
        private int groupId;
}
