package com.gym.convivials.dto;

import com.gym.convivials.entities.GymGroup;
import com.gym.convivials.entities.Gyms;
import com.gym.convivials.enums.Gender;
import com.gym.convivials.enums.WorkoutGoals;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalTime;

@Data
public class PartnerPreferenceDto {
    private int id;
    @NotNull
    private WorkoutGoals workoutGoals;
    private Gender gender;
    private Integer userId;
    private Integer  gymId;
    private Integer  gymGroupId;
    @NotNull
    private LocalTime preferredTime;
    private Double vicinity;
}
