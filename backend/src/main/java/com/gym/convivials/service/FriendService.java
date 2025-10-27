package com.gym.convivials.service;

import com.gym.convivials.dto.FriendRequestActionRequest;
import com.gym.convivials.dto.FriendshipDto;
import com.gym.convivials.dto.UserDto;
import com.gym.convivials.entities.Friendships;
import com.gym.convivials.entities.User;
import com.gym.convivials.enums.FriendshipStatus;
import com.gym.convivials.mapper.FriendshipMapper;
import com.gym.convivials.mapper.UserMapper;
import com.gym.convivials.repository.FriendshipRepository;
import com.gym.convivials.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FriendService {
    @Autowired
    private FriendshipMapper friendshipMapper;
    @Autowired
    private FriendshipRepository friendshipRepository;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserMapper userMapper;

    private static final Logger logger = LoggerFactory.getLogger(FriendService.class);


    public String sendFriendRequest(FriendshipDto friendReq) {
        Integer reqId=friendReq.getRequesterId();
        Integer recId=friendReq.getReceiverId();
        Friendships friendship=friendshipRepository.findByRequesterUserIdAndReceiverUserId(reqId,recId);
        Friendships friend=null;
        if(friendship==null){
            friendReq.setCreatedAt(OffsetDateTime.now());
            friend = friendshipMapper.toEntity(friendReq);
            friend=friendshipRepository.save(friend);
        }
        else if(friendship.getStatus()==FriendshipStatus.REJECTED || friendship.getStatus()== FriendshipStatus.CANCELLED){
            friendship.setUpdatedAt(OffsetDateTime.now());
            friendship.setStatus(FriendshipStatus.PENDING);
            friend=friendshipRepository.save(friendship);
        }
        else{
            return "Invalid request";
        }
        return friend.getStatus().toString();
    }



    public List<FriendshipDto> getPendingRequest(int receiverId, String action) {
        List<Friendships> friendships=new ArrayList<>();
        if(action.equals("received"))
            friendships= friendshipRepository.findByReceiverUserId(receiverId);
        if(action.equals("sent"))
             friendships = friendshipRepository.findByRequesterUserId(receiverId);
        if (!friendships.isEmpty()) {
            List<FriendshipDto> result = friendships.stream().map(friendship -> friendshipMapper.toDto(friendship)).toList();
            return result;
        }
        return null;
    }


    public String updateFriendshipStatus(FriendRequestActionRequest requestObject) {
        String response="";
        Friendships friendship=friendshipRepository.findByRequesterUserIdAndReceiverUserId(requestObject.getRequesterId(),requestObject.getReceiverId());
        friendship.setUpdatedAt(OffsetDateTime.now());
        if(requestObject.getStatus()==FriendshipStatus.ACCEPTED) {
            if (friendship != null && friendship.getStatus() == FriendshipStatus.PENDING) {
                friendship.setStatus(FriendshipStatus.ACCEPTED);
                response = "Accepted Successfully";
            }
            else{
                return "Invalid accept request";
            }
        }
        else if(requestObject.getStatus()==FriendshipStatus.CANCELLED){
            if (friendship != null || friendship.getStatus() != FriendshipStatus.CANCELLED || friendship.getStatus() != FriendshipStatus.REJECTED || friendship.getStatus() != FriendshipStatus.BLOCKED) {
                friendship.setStatus(FriendshipStatus.CANCELLED);
                response= "Cancelled request successfully";
            } else {
                return "Invalid cancel request";
            }
        }
        else if(requestObject.getStatus()==FriendshipStatus.REJECTED){
            if (friendship == null || friendship.getStatus() == FriendshipStatus.PENDING || friendship.getStatus() == FriendshipStatus.ACCEPTED ) {
                friendship.setStatus(FriendshipStatus.REJECTED);
                response = "Rejected Successfully";
            }
            else{
                return "Invalid reject request";
            }
        }
        else if(requestObject.getStatus()==FriendshipStatus.BLOCKED){
            if(friendship!=null ||friendship.getStatus()!=FriendshipStatus.BLOCKED) {
                friendship.setStatus(FriendshipStatus.BLOCKED);
                response = "Rejected Successfully";
            }
            else{
                return "Invalid block request";
            }
        }
        else if(requestObject.getStatus()==FriendshipStatus.REMOVED){
            if(friendship!=null ||friendship.getStatus()==FriendshipStatus.ACCEPTED) {
                friendship.setStatus(FriendshipStatus.BLOCKED);
                response = "Rejected Successfully";
            }
            else{
                return "Invalid block request";
            }
        }
        friendshipRepository.save(friendship);
        return response;
    }


    public List<UserDto> getFriends(int userId) {
        List<Integer> friendsUserId= friendshipRepository.findFriendIdsByUserId(userId);
        List<User> friends= userRepo.findByUserIdIn(friendsUserId);
        List<UserDto> friendDto=friends.stream().map((user)->userMapper.toDto(user)).toList();
        return friendDto;
    }
}
