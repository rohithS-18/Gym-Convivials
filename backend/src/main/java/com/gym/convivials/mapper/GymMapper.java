package com.gym.convivials.mapper;

import com.gym.convivials.dto.GymDto;
import com.gym.convivials.entities.Gyms;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface GymMapper {
    @Mapping(target="group.id", source="dto.groupId")
    @Mapping(target = "location", expression = "java(fromCoordinates(dto.getLatitude(),dto.getLongitude()))")
    Gyms toEntity(GymDto dto);


    @Mapping(source="entity.group.id", target="groupId")
    @Mapping(target = "latitude", expression = "java(toLatitude(entity.getLocation()))")
    @Mapping(target = "longitude", expression = "java(toLongitude(entity.getLocation()))")
    GymDto toDto(Gyms entity);

    public default double toLatitude(Point point) {
        return point != null ? point.getY() : 0;
    }

    public default  double toLongitude(Point point) {
        return point != null ? point.getX() : 0;
    }

    public default Point fromCoordinates(double latitude, double longitude) {
        GeometryFactory factory = new GeometryFactory(new PrecisionModel(), 4326);
        return factory.createPoint(new Coordinate(longitude, latitude));
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateGymFromDto(GymDto dto, @MappingTarget Gyms entity);
}
