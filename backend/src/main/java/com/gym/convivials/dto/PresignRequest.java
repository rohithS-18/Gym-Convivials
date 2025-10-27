package com.gym.convivials.dto;

import lombok.Data;

@Data
public class PresignRequest {
    private String filename;
    private String contentType;
    private int userId;
    private String category;
    private String username;
}
