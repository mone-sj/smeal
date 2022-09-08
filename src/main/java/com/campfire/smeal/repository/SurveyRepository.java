package com.campfire.smeal.repository;

import com.campfire.smeal.model.SurveyFoodMbti;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SurveyRepository extends JpaRepository<SurveyFoodMbti, Integer> {


}
