package com.campfire.smeal.service;

import com.campfire.smeal.model.mbti.SurveyFoodMbti;
import com.campfire.smeal.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final SurveyRepository surveyRepository;

    @Transactional
    public List<SurveyFoodMbti> surveyFindAll() {
         return surveyRepository.findAll();
    }
}
