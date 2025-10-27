package com.gym.convivials.dto;

import com.gym.convivials.enums.FriendshipStatus;
import lombok.Data;
import java.time.OffsetDateTime;

@Data
public class FriendshipDto {
    private int id;
    private int requesterId;
    private int receiverId;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private FriendshipStatus status;
}
