package com.gym.convivials.service;

import com.gym.convivials.dao.UserDao;
import com.gym.convivials.entities.UserPrincipal;
import com.gym.convivials.entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    public UserDao userdao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Users user=userdao.findUserByUsername(username);
//       System.out.println(user.getUsername());
//       System.out.println(user.getPasword());
       if(user==null){
           throw new  UsernameNotFoundException("user not found");
       }
       return new UserPrincipal(user);
    }
}
