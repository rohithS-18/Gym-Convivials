package com.gym.convivials.dao;

import com.gym.convivials.entities.Users;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass= Users.class,idClass = Integer.class)
public interface UserDao {
    public Users findUserByUsername(String username);
    public Users save(Users user);
}
