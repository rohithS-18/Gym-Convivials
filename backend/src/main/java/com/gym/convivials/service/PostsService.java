package com.gym.convivials.service;

import com.gym.convivials.dto.PostDto;
import com.gym.convivials.entities.Post;
import com.gym.convivials.mapper.PostMapper;
import com.gym.convivials.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
public class PostsService {
    @Autowired
    private PostMapper mapper;
    @Autowired
    private PostRepository postrepo;

    public int uploasPost(PostDto data)  {
        Post uploadedPost=postrepo.save(mapper.toEntity(data));
        return uploadedPost.getId();
    }
}
