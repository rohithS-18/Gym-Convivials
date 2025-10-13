package com.gym.convivials.mapper;

import com.gym.convivials.dto.UserDto;
import com.gym.convivials.entities.User;
import org.locationtech.jts.geom.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "latitude", expression = "java(toLatitude(user.getLocation()))")
    @Mapping(target = "longitude", expression = "java(toLongitude(user.getLocation()))")
    UserDto toDto(User user);

    @Mapping(target = "location", expression = "java(fromCoordinates(dto.getLatitude(), dto.getLongitude()))")
    User toEntity(UserDto dto);

    default double toLatitude(Point point) {
        return point != null ? point.getY() : 0;
    }

    default double toLongitude(Point point) {
        return point != null ? point.getX() : 0;
    }

    default Point fromCoordinates(double latitude, double longitude) {
        GeometryFactory factory = new GeometryFactory(new PrecisionModel(), 4326);
        return factory.createPoint(new Coordinate(longitude, latitude));
    }
}

