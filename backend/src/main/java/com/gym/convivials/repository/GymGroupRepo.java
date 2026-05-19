package com.gym.convivials.repository;

import com.gym.convivials.entities.GymGroup;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = GymGroup.class,idClass = Integer.class)
public interface GymGroupRepo {
    GymGroup save(GymGroup gymGroup);
    GymGroup findById(int id);
}
