package com.campfire.smeal.repository;

import com.campfire.smeal.model.MbtiType;
import com.campfire.smeal.model.SurveyResultFoodMbti;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MbtiTypeRepository extends JpaRepository<MbtiType, Integer> {

}
