package com.gym.convivials.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.locationtech.jts.geom.Point;

import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.Set;

@Data
public class GymDto {
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private String city;
    private LocalTime opensAt;
    private LocalTime closesAt;
    private double latitude;
    private double longitude;
    private Set<String> holidays;
    private int groupId;
}
