package com.campfire.smeal.service;

import com.campfire.smeal.config.BCryptEnc;
import com.campfire.smeal.model.RoleType;
import com.campfire.smeal.model.User;
import com.campfire.smeal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public User 회원수정(User user) {
        //User persistance = userRepository.findById(user.getId()).orElseThrow(() -> {
        User persistance = userRepository.findById(1L).orElseThrow(() -> {
            return new IllegalArgumentException("회원 찾기 실패");
        });

        String rawPassword = user.getPassword();
        String encPassword = bCryptEnc.encodePWD().encode(rawPassword);
        persistance.setPassword(encPassword);
        persistance.setAge(user.getAge());
        persistance.setGender(user.getGender());
        persistance.setEmail(user.getEmail());
        persistance.setNickname(user.getNickname());

        return persistance;
    }

    @Transactional
    public void 회원삭제(Long id) {
        userRepository.deleteById(id);
    }
}
