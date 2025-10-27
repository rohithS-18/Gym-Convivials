package com.gym.convivials.mapper;

import com.gym.convivials.dto.PostDto;
import com.gym.convivials.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "userId", source = "user.userId")
    public   PostDto toDto(Post entity);
    @Mapping(target="user.userId", source = "userId")
    public  Post toEntity(PostDto dto);
}

