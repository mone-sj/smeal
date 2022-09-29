package com.campfire.smeal.service;

import com.campfire.smeal.handler.exception.GeneralException;
import com.campfire.smeal.handler.exception.SmErrorCode;
import com.campfire.smeal.model.RoleType;
import com.campfire.smeal.model.User;
import com.campfire.smeal.model.mbti.SurveyFoodMbti;
import com.campfire.smeal.repository.SurveyRepository;
import com.campfire.smeal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final SurveyRepository surveyRepository;
    private final UserRepository userRepository;


    @Transactional
    public List<SurveyFoodMbti> surveyFindAll() {
        return surveyRepository.findAll();
    }

    @Transactional
    public List<User> userList() {
        return userRepository.findAll();
    }

    @Transactional
    public User memLevelUp(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new GeneralException(SmErrorCode.NO_USER));
        if (user != null) {
            user.setRole(RoleType.ROLE_ADMIN);
            //user.setRole(RoleType.ROLE_MANAGER);
        }
        return user;
    }

}
