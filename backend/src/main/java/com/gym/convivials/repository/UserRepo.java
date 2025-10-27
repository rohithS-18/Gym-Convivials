package com.gym.convivials.repository;

import com.gym.convivials.entities.User;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;

@RepositoryDefinition(domainClass= User.class,idClass = Integer.class)
public interface UserRepo {
    public User findUserByUsername(String username);
    public User save(User user);
    public User findByUserId(int userId);
    public List<User> findByUserIdIn(List<Integer> userIds);
}
