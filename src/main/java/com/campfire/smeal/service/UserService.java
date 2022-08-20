package com.campfire.smeal.service;

import com.campfire.smeal.config.BCryptEnc;
import com.campfire.smeal.handler.exception.GeneralException;
import com.campfire.smeal.handler.exception.SmErrorCode;
import com.campfire.smeal.model.RoleType;
import com.campfire.smeal.model.User;
import com.campfire.smeal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.campfire.smeal.handler.exception.SmErrorCode.DUPLICATED_USER_ID;
import static com.campfire.smeal.handler.exception.SmErrorCode.INVALID_REQUEST;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    private final BCryptEnc bCryptEnc;

    @Transactional
    public void 회원가입(User user) {
        try {
                String rawPassword = user.getPassword();
                String encPassword = bCryptEnc.encodePWD().encode(rawPassword);
                user.setPassword(encPassword);
                user.setRole(RoleType.USER);
                userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new GeneralException(DUPLICATED_USER_ID);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GeneralException(INVALID_REQUEST);
        }
    }

    @Transactional
    public User 회원수정(User user) {
        //User persistence = userRepository.findById(user.getId()).orElseThrow(() -> {
        User persistence = userRepository.findById(4L).orElseThrow(() -> {
            return new GeneralException(SmErrorCode.NO_USER);
        });

        if (persistence.getProvider() == null || persistence.getProvider().equals("")) {
            String rawPassword = user.getPassword();
            String encPassword = bCryptEnc.encodePWD().encode(rawPassword);
            persistence.setPassword(encPassword);
            persistence.setAge(user.getAge());
            persistence.setGender(user.getGender());
            persistence.setEmail(user.getEmail());
            persistence.setNickname(user.getNickname());
        }

        return persistence;
    }

    @Transactional
    public void 회원삭제(Long id) {
        userRepository.deleteById(id);
    }


    public Boolean validateCreateUser(String username) {
        if (userRepository.findByUsername(username) != null) {
            throw new GeneralException(DUPLICATED_USER_ID);
        }
        return true;
    }
}
