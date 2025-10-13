package com.gym.convivials.controllers;

import com.gym.convivials.dao.UserDao;
import com.gym.convivials.dto.UserDto;
import com.gym.convivials.service.JWTService;
import com.gym.convivials.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class UserController {
    private final UserDao userDao;
    private final UserService userservice;
    private final JWTService jwtService;



    @PostMapping("/mlogin")
    public ResponseEntity<String> login(@RequestBody UserDto user, HttpServletResponse response){
        String jwt=userservice.verify(user);
        return new ResponseEntity<>(jwt, HttpStatus.OK);
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto user){
        UserDto userdto=userservice.registerUser(user);
        return new ResponseEntity<>(userdto,HttpStatus.CREATED);
    }

    @GetMapping("/get-cookie")
    public String getCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    System.out.println("Cookie Value: " + cookie.getValue());
                    return "Cookie Value: " + cookie.getValue();
                }
            }
        }
        return "Cookie not found!";
    }

//    @GetMapping("/getToken")
//    public ResponseEntity<String> getToken(@CookieValue(value = "refreshToken") String token){
//        if(token!=null){
//            String username=jwtService.extractUsername(token);
//            String accessToken=jwtService.generateToken(username,30,"accessToken");
//            return new ResponseEntity<>(accessToken,HttpStatus.OK);
//        }
//        return new ResponseEntity<>("Not authorized",HttpStatus.UNAUTHORIZED);
//    }

    @PutMapping("/upateUserProfile")
    public ResponseEntity<?> updateProfile(@RequestBody UserDto user){
        userservice.updateUserDetails(user);
        return ResponseEntity.ok("Upated user profile successfully");
    }

    @GetMapping("/test")
    public ResponseEntity<String> testapi(){
        return new ResponseEntity<>("Info",HttpStatus.OK);
    }
}
