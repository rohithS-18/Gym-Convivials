package com.gym.convivials.dto;

import lombok.Data;

@Data
public class PartnerResponse {
    private int userId;
    private String name;
    private String profilePicUrl;
    private Double matchPercentage;
}
