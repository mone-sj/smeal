package com.campfire.smeal.repository;

import com.campfire.smeal.model.mbti.SurveyFoodMbti;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRepository extends JpaRepository<SurveyFoodMbti, Integer> {


}
