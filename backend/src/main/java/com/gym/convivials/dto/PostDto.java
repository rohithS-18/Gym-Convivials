package com.gym.convivials.dto;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.Date;

@Data
public class PostDto {
    private int id;
    private String caption;
    private OffsetDateTime createdAt;
    private int userId;
    private String postUrl;
}
