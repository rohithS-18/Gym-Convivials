package com.gym.convivials.service;

import com.gym.convivials.mapper.UserMapper;
import com.gym.convivials.repository.UserRepo;
import com.gym.convivials.dto.UserDto;
import com.gym.convivials.entities.User;
import com.gym.convivials.entities.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
    @Autowired
    public UserRepo userdao;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private UserMapper mapper;

    public UserDto registerUser(UserDto userDto){
        BCryptPasswordEncoder encoder =new BCryptPasswordEncoder(12);
        userDto.setPasword(encoder.encode(userDto.getPasword()));
        User user=mapper.toEntity(userDto);
        userdao.save(user);
        UserDto userdto=mapper.toDto(user);
        return userdto;
    }

    public String verify(UserDto user){
        log.debug("Entering into verify for authentication");
        log.debug("Received user details "+user.getUsername()+" "+user.getPasword());
        Authentication auth=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPasword()));
        if(auth.isAuthenticated()){
            log.debug("User "+user.getUsername()+" successfully authenticated");
            UserPrincipal principleUser=(UserPrincipal)auth.getPrincipal();
            User authenticatedUser=principleUser.getUser();
            return jwtService.generateToken(principleUser);
        }
        else{
            log.debug("User "+user.getUsername()+"  authenticated failed");
            return "failure";
        }
    }

    public void updateUserDetails(UserDto userDto) {
        User user=userdao.findByUserId(userDto.getUserId());
        user.setDOB(userDto.getDOB());
        GeometryFactory factory = new GeometryFactory(new PrecisionModel(), 4326);
        user.setLocation(factory.createPoint(new Coordinate(userDto.getLongitude(), userDto.getLatitude())));
        user.setProfilePic(userDto.getProfilePic());
        user.setGender(userDto.getGender());
        user=userdao.save(user);
        System.out.println("here");
    }
}
