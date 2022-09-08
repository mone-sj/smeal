package com.campfire.smeal.service;

import com.campfire.smeal.dto.mbti.MbtiResponseDto;
import com.campfire.smeal.model.MbtiType;
import com.campfire.smeal.model.SurveyFoodMbti;
import com.campfire.smeal.model.SurveyResultFoodMbti;
import com.campfire.smeal.repository.MbtiTypeRepository;
import com.campfire.smeal.repository.SurveyRepository;
import com.campfire.smeal.repository.SurveyResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MbtiService {

    private final SurveyRepository surveyRepository;
    private final SurveyResultRepository surveyResultRepository;
    private final MbtiTypeRepository mbtiTypeRepository;

    @Transactional (readOnly = true)
    public List<SurveyFoodMbti> surveyRead(){
        return surveyRepository.findAll();
    }

    @Transactional
    public void surveyResultSave(ArrayList<MbtiResponseDto> mbtiResponseDtos){
        for (int i = 0; i < mbtiResponseDtos.size(); i++) {
            surveyResultRepository.mSave(mbtiResponseDtos.get(i).getQNo(),
                    mbtiResponseDtos.get(i).getResult(),
                    mbtiResponseDtos.get(i).getResultTypeCode());
        }

    }

}
