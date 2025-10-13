package com.gym.convivials.dao;

import com.gym.convivials.entities.User;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass= User.class,idClass = Integer.class)
public interface UserDao {
    public User findUserByUsername(String username);
    public User save(User user);
    public User findUserByUserId(int userId);
}
