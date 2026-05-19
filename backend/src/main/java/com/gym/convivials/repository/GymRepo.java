package com.gym.convivials.repository;

import com.gym.convivials.entities.Gyms;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.List;

@RepositoryDefinition(domainClass = Gyms.class,idClass = Integer.class)
public interface GymRepo {
    Gyms save(Gyms gym);
    Gyms findByName(String name);
    Gyms findById(int id);
    @Query("Select g from Gyms g where ST_DISTANCE(g.location,:point)<=:meters and g.group.id=:groupId")
    List<Gyms> findGymsByGroupAndDistance(@Param("point")Point location,@Param("meters") double meters,@Param("groupId")int groupId);


}
