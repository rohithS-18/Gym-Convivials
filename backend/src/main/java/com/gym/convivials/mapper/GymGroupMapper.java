package com.gym.convivials.mapper;

import com.gym.convivials.dto.GymDto;
import com.gym.convivials.dto.GymGroupDto;
import com.gym.convivials.entities.GymGroup;
import com.gym.convivials.entities.Gyms;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface GymGroupMapper {
    GymGroup toEntity(GymGroupDto dto);

    GymGroupDto toDto(GymGroup entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateGymFromDto(GymGroupDto dto, @MappingTarget GymGroup entity);
}
