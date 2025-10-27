package com.gym.convivials.repository;

import com.gym.convivials.entities.Post;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Post.class,idClass = Integer.class)
public interface PostRepository {
    public Post save(Post post);

}
