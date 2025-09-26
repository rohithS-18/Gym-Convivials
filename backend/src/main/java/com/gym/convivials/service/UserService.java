package com.gym.convivials.service;

import com.gym.convivials.dao.UserDao;
import com.gym.convivials.dto.UserDto;
import com.gym.convivials.entities.Users;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    public UserDao userdao;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTService jwtService;

    public UserDto registerUser(UserDto userDto){
        BCryptPasswordEncoder encoder =new BCryptPasswordEncoder(12);
        userDto.setPasword(encoder.encode(userDto.getPasword()));
        Users user= new Users();
        BeanUtils.copyProperties(userDto,user);
        user=userdao.save(user);
        UserDto userdto=new UserDto();
        BeanUtils.copyProperties(user,userdto);
        return userdto;
    }

    public String verify(UserDto user){
        Authentication auth=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPasword()));
        if(auth.isAuthenticated()){
            return jwtService.generateToken(user.getUsername());
        }
        else{
            return "failure";
        }
    }
}
