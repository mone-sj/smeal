package com.campfire.smeal.config.auth;

import com.campfire.smeal.model.User;
import com.campfire.smeal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User userEntity = userRepository.findByUsername(username);
//        if (userEntity != null) {
//            return new PrincipalDetails(userEntity);
//        }
//
//        return null;
//    }

    //username에 대해서 체크 필요(userId로는 안되나?)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userRepository.findByUsername(username);
        if (userEntity != null) {
            return new PrincipalDetails(userEntity);
        }

        return null;
    }
}
