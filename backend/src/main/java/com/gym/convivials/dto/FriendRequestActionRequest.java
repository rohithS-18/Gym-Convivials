package com.gym.convivials.dto;

import com.gym.convivials.enums.FriendshipStatus;
import lombok.Data;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
@Data
public class FriendRequestActionRequest {
     @NotNull private int receiverId;
     @NotNull private int requesterId;
     private FriendshipStatus status;
}
