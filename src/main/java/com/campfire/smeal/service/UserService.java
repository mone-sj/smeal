package com.campfire.smeal.service;

import com.campfire.smeal.config.BCryptEnc;
import com.campfire.smeal.handler.exception.GeneralException;
import com.campfire.smeal.handler.exception.SmErrorCode;
import com.campfire.smeal.model.RoleType;
import com.campfire.smeal.model.User;
import com.campfire.smeal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.campfire.smeal.handler.exception.SmErrorCode.*;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserService {
    private final UserRepository userRepository;

    private final BCryptEnc bCryptEnc;

    @Transactional
    public void 회원가입(User user, String passwordRepeat) {
        try {
            if (!passwordRepeat.equals(user.getPassword())) {
                throw new GeneralException(INCONSISTENCY_PASSWORD);
            }
            String rawPassword = user.getPassword();
            String encPassword = bCryptEnc.encodePWD().encode(rawPassword);
            user.setPassword(encPassword);
            user.setUserId(user.getUsername());
            user.setRole(RoleType.ROLE_USER);
            userRepository.save(user);
        } /*catch (DataIntegrityViolationException ex) {
            log.info("3");
            throw new GeneralException(DUPLICATED_USER_ID);
        }*/
        catch (Exception e) {
            e.printStackTrace();
            throw new GeneralException(INVALID_REQUEST);
        }
    }

    @Transactional
    public User 회원수정(User user, String passwordRepeat) {
        if (!user.getPassword().equals(passwordRepeat)) {
            throw new GeneralException(SmErrorCode.INCONSISTENCY_PASSWORD);
        }

        User persistence = userRepository.findById(user.getId()).orElseThrow(() -> {
            //User persistence = userRepository.findByUserId(user.getUserId()).orElseThrow(() -> {
            return new GeneralException(SmErrorCode.NO_USER);
        });

        String rawPassword = user.getPassword();
        String encPassword = bCryptEnc.encodePWD().encode(rawPassword);
        persistence.setPassword(encPassword);
        persistence.setAge(user.getAge());
        persistence.setGender(user.getGender());
        persistence.setEmail(user.getEmail());
        persistence.setNickname(user.getNickname());

        return persistence;
    }

    @Transactional
    public User 회원찾기(String username){

        User user = userRepository.findByUserId(username).orElseGet(() -> { // 찾아보고 없으면 빈객체를 생성해라
            return new User();
        });
        if (user.getUserId() == null) {
            System.out.println("ID가 없거나 잘 못 입력하셨습니다.");
            throw new GeneralException(LOGIN_ERROR);
        }
        return user;
    }

    @Transactional
    public void 회원삭제(Long id) {
        userRepository.deleteById(id);
    }

    public Boolean validateCreateUser(String userId) {
        if (userRepository.findByUserId(userId) != null) {
            throw new GeneralException(DUPLICATED_USER_ID);
        }
        return true;
    }

    @Transactional
    public void 회원정보추가(Long id, String gender, String age, String resultTypeCode) {
        User persistence = userRepository.findById(id).orElseThrow(() ->{
            return new GeneralException(SmErrorCode.NO_USER);
        });

        persistence.setGender(gender);
        persistence.setAge(age);
        persistence.setFoodMbti(resultTypeCode);

    }

}
