package com.gym.convivials.dto;

import com.gym.convivials.entities.User;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class GymGroupDto {
    private Integer id;
    private String name;
}
