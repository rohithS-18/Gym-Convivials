package com.gym.convivials.controllers;

import com.gym.convivials.dao.UserDao;
import com.gym.convivials.dto.UserDto;
import com.gym.convivials.entities.Users;
import com.gym.convivials.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userservice;

    @PostMapping("/mlogin")
    public ResponseEntity<String> login(@RequestBody UserDto user){
        return new ResponseEntity<>(userservice.verify(user), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto user){
        UserDto userdto=userservice.registerUser(user);
        return new ResponseEntity<>(userdto,HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<String> testapi(){
        return new ResponseEntity<>("Info",HttpStatus.OK);
    }
}
