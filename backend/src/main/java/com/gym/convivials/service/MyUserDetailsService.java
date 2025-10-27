package com.gym.convivials.service;

import com.gym.convivials.repository.UserRepo;
import com.gym.convivials.entities.UserPrincipal;
import com.gym.convivials.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    public UserRepo userdao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user=userdao.findUserByUsername(username);
//       System.out.println(user.getUsername());
//       System.out.println(user.getPasword());
       if(user==null){
           throw new  UsernameNotFoundException("user not found");
       }
       return new UserPrincipal(user);
    }
}
