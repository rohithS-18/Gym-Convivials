package com.gym.convivials.repository;

import com.gym.convivials.entities.Friendships;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.List;

@RepositoryDefinition(domainClass = Friendships.class,idClass = Integer.class)
public interface FriendshipRepository {
    public Friendships save(Friendships friendship);
    public Friendships findByRequesterUserIdAndReceiverUserId(int userId,int userid);
    public List<Friendships> findByReceiverUserId(int receiverId);
    List<Friendships> findByRequesterUserId(int requesterId);
    @Query("SELECT CASE WHEN f.requester.userId = :userId THEN f.receiver.userId ELSE f.requester.userId END " +
            "FROM Friendships f " +
            "WHERE f.status = 'ACCEPTED' AND (:userId IN (f.requester.userId, f.receiver.userId))")
    List<Integer> findFriendIdsByUserId(@Param("userId") int userId);


}
