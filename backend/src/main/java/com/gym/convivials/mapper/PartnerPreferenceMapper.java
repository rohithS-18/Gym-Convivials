package com.gym.convivials.mapper;

import com.gym.convivials.dto.PartnerPreferenceDto;
import com.gym.convivials.entities.PartnerPreference;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PartnerPreferenceMapper {
    @Mapping(target = "gym.id", source = "dto.gymId")
    @Mapping(target = "gymGroup.id", source = "dto.gymGroupId")
    @Mapping(target="user.userId", source="dto.userId")
    PartnerPreference toEntity(PartnerPreferenceDto dto);

    @Mapping(target = "gymId", source = "entity.gym.id")
    @Mapping(target = "gymGroupId", source = "entity.gymGroup.id")
    @Mapping(target = "userId",source = "entity.user.userId")
    PartnerPreferenceDto toDto(PartnerPreference entity);
}
