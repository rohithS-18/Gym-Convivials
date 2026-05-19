package com.gym.convivials.dto;

import com.gym.convivials.entities.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Data
public class GymImageDto {
    private int id;
    private User createdBy;
    private OffsetDateTime createdAt;
    private User updatedBy;
    private OffsetDateTime updatedAt;
    private int  gymID;
    private String imageURL;
    private String caption;
}
