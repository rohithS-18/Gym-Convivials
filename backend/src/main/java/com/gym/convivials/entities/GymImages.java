package com.gym.convivials.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "gym_images")
public class GymImages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;
    @Column(name = "created_at")
    @CreationTimestamp
    private OffsetDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;
    @ManyToOne
    @JoinColumn(name = "gym_id")
    private Gyms gymID;
    @Column(name = "image_url")
    private String imageURL;
    private String caption;
}
