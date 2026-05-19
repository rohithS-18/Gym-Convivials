package com.gym.convivials.mapper;

import com.gym.convivials.dto.UserDto;
import com.gym.convivials.entities.Role;
import com.gym.convivials.entities.User;
import com.gym.convivials.repository.RoleRepo;
import org.locationtech.jts.geom.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring")
public abstract class UserMapper {
    @Autowired
    private RoleRepo roleRepo;

    @Mapping(target = "latitude", expression = "java(toLatitude(entity.getLocation()))")
    @Mapping(target = "longitude", expression = "java(toLongitude(entity.getLocation()))")
    @Mapping(target="roles", expression = "java(getRoleNames(entity.getRoles()))")
    @Mapping(target="gymId" ,source="entity.gym.id")
    @Mapping(target="groupId" ,source="entity.group.id")
    public abstract UserDto toDto(User entity);

    @Mapping(target="roles", expression = "java(getRoleByName(dto.getRoles()))")
    @Mapping(target = "location", expression = "java(fromCoordinates(dto.getLatitude(), dto.getLongitude()))")
    @Mapping(target="gym.id" ,source="dto.gymId")
    @Mapping(target="group.id" ,source="dto.groupId")
    public abstract User toEntity(UserDto dto);



    public Set<String> getRoleNames(Set<Role> roles){
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }

    public Set<Role> getRoleByName(Set<String> roles){
        return roleRepo.findAllRolesByName(roles);
    }

    public double toLatitude(Point point) {
        return point != null ? point.getY() : 0;
    }

    public double toLongitude(Point point) {
        return point != null ? point.getX() : 0;
    }

    public Point fromCoordinates(double latitude, double longitude) {
        GeometryFactory factory = new GeometryFactory(new PrecisionModel(), 4326);
        return factory.createPoint(new Coordinate(longitude, latitude));
    }
}

