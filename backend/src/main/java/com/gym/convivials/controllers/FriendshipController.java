package com.gym.convivials.controllers;

import com.gym.convivials.dto.FriendRequestActionRequest;
import com.gym.convivials.dto.FriendshipDto;
import com.gym.convivials.dto.UserDto;
import com.gym.convivials.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FriendshipController {
    @Autowired
    private FriendService friendService;

    @PostMapping("/send-friend-request")
    public ResponseEntity<String> sendFriendReq(@RequestBody FriendshipDto friendReq){
        String status=friendService.sendFriendRequest(friendReq);
        if(status.equals("Invalid request") || status.equals("friendship exists")){
            return new ResponseEntity<>(status,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(status,HttpStatus.OK);
    }

    @GetMapping("/pending-requests")
    public ResponseEntity<List<FriendshipDto>> getPendingReceivedRequests(@RequestParam int receiverId){
        List<FriendshipDto> pendingRequests=friendService.getPendingRequest(receiverId,"received");
            return new ResponseEntity<>(pendingRequests,HttpStatus.OK);
    }

    @PutMapping("modify-request")
    public ResponseEntity<String> cancelFriendRequest(@RequestBody FriendRequestActionRequest requestObject){
        String res=friendService.updateFriendshipStatus(requestObject);
        if(res.startsWith("Invalid")){
            return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    @GetMapping("/get-friends")
    public ResponseEntity<List<UserDto>> getFriends(@RequestParam int userId){
        List<UserDto> friendList=friendService.getFriends(userId);
        return new ResponseEntity<>(friendList,HttpStatus.OK);
    }

    @GetMapping("/sent-requests")
    public ResponseEntity<List<FriendshipDto>> getPendingSentRequests(@RequestParam int receiverId){
        List<FriendshipDto> pendingRequests=friendService.getPendingRequest(receiverId, "sent");
        return new ResponseEntity<>(pendingRequests,HttpStatus.OK);
    }
}
