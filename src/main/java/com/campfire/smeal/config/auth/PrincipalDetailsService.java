package com.campfire.smeal.config.auth;

import com.campfire.smeal.handler.exception.GeneralException;
import com.campfire.smeal.handler.exception.SmErrorCode;
import com.campfire.smeal.model.User;
import com.campfire.smeal.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User userEntity = userRepository.findByUserId(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다."));
        log.info(String.valueOf(userEntity));

        return new PrincipalDetails(userEntity);
    }

}
