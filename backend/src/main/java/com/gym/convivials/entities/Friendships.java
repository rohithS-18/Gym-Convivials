package com.gym.convivials.entities;

import com.gym.convivials.enums.FriendshipStatus;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;


@Getter
@Setter
@Entity
@Table(
        name = "friendships",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"requester_id", "receiver_id"})
        }
)
public class Friendships {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name ="requester_id")
    private User requester;
    @ManyToOne
    @JoinColumn(name ="receiver_id")
    private User receiver;
    @CreationTimestamp
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FriendshipStatus status= FriendshipStatus.PENDING;
}
