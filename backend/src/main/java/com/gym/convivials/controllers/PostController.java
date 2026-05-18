package com.gym.convivials.controllers;

//import com.gym.convivials.service.PostsService;
import com.gym.convivials.dto.PostDto;
import com.gym.convivials.service.PostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class PostController {
    @Autowired
    private PostsService postsService;

    @PostMapping("/upload-posts")
    public ResponseEntity<?> createPost(@RequestBody PostDto postData) throws IOException {
        int postId=postsService.uploasPost(postData);
        System.out.println("post-Id "+postId);
        return new ResponseEntity<>("post uploaded succcessfully"+postId,HttpStatus.OK);
    }

}
