package com.gym.convivials.entities;

import com.gym.convivials.enums.Gender;
import com.gym.convivials.enums.WorkoutGoals;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Getter
@Setter
public class PartnerPreference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "workout_goals")
    private WorkoutGoals workoutGoals;
    @Column(name = "gender_identity")
    private Gender gender;
    @ManyToOne
    @JoinColumn(name = "gym_id")
    private Gyms gym;
    @ManyToOne
    @JoinColumn(name = "gym_group_id")
    private GymGroup gymGroup;
    @Column(name="preferred_time")
    private LocalTime preferredTime;
    private Double vicinity;
}
