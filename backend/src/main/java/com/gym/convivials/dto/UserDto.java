package com.gym.convivials.dto;


import lombok.Builder;
import lombok.Data;
import java.util.Date;

@Data
public class UserDto {
        private int userId;
        private String username;
        private String email;
        private String pasword;
        private String city;
        private String profilePic;
        private Date DOB;
        private double latitude;
        private double longitude;
        private String gender;
}
