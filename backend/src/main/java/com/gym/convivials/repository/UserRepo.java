package com.gym.convivials.repository;

import com.gym.convivials.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.List;

@RepositoryDefinition(domainClass= User.class,idClass = Integer.class)
public interface UserRepo {
    public User findUserByUsername(String username);
    public User save(User user);
    public User findByUserId(int userId);
    public List<User> findByUserIdIn(List<Integer> userIds);
    public List<User> findByGymId(int gymId);
    @Query("Select u from User u where u.group.id=:groupId and u.city=:city")
    public List<User> findByGroupAndCity(@Param("groupId") int groupId, @Param("city") String city);
    public List<User> findByGroupId(int groupId);
}
