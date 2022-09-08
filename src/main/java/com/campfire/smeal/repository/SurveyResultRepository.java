package com.campfire.smeal.repository;

import com.campfire.smeal.model.SurveyResultFoodMbti;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SurveyResultRepository extends JpaRepository<SurveyResultFoodMbti, Integer> {

    @Modifying
    @Query(value = "INSERT INTO SURVEYRESULTFOODMBTI(surveyFoodMbti_qNo, result, mbtiResult_typeCode) " +
            "VALUES(?1, ?2, ?3)", nativeQuery = true)
    int mSave(String surveyFoodMbtiQno, String result, String mbtiTypeTypeCode);


}
