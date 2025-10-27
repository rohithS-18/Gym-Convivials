package com.gym.convivials.mapper;

import com.gym.convivials.dto.FriendshipDto;
import com.gym.convivials.entities.Friendships;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface  FriendshipMapper {

    @Mapping(target="requester.userId" , source = "requesterId")
    @Mapping(target="receiver.userId" , source = "receiverId")
    public  Friendships toEntity(FriendshipDto dto);

    @Mapping(target="requesterId" , source = "requester.userId")
    @Mapping(target="receiverId" , source = "receiver.userId")
    public FriendshipDto toDto(Friendships entity);

}
