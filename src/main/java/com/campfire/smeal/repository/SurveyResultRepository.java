package com.campfire.smeal.repository;

import com.campfire.smeal.dto.memberStatusDto;
import com.campfire.smeal.model.mbti.SurveyResultFoodMbti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SurveyResultRepository extends JpaRepository<SurveyResultFoodMbti, Integer> {

    @Modifying
    @Query(value = "INSERT INTO SURVEYRESULTFOODMBTI(surveyFoodMbti_qNo, result, mbtiResult_typeCode, age, gender, memberStatus) " +
            "VALUES(?1, ?2, ?3, ?4, ?5, ?6)", nativeQuery = true)
    int mSave(String surveyFoodMbtiQno, String result, String mbtiTypeTypeCode, String age, String gender, String memberStatus);



    @Query(value = "select memberStatus, count(memberStatus) as cnt from surveyresultfoodmbti where surveyFoodMbti_qNo='Q1' group by memberStatus", nativeQuery = true)
    List<memberStatusDto> memStatusRatio();
}
