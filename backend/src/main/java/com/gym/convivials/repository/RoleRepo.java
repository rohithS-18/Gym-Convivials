package com.gym.convivials.repository;

import com.gym.convivials.entities.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.Set;

@RepositoryDefinition(domainClass = Role.class,idClass = Integer.class)
public interface RoleRepo {
    public Role findByName(String name);

    @Query("Select r from Role r where name IN :names")
    public Set<Role> findAllRolesByName(@Param("names") Set<String> names);
}
