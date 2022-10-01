package com.campfire.smeal.repository;

import com.campfire.smeal.dto.searchFood.FoodRankTypeDto;
import com.campfire.smeal.model.mbti.MbtiType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MbtiTypeRepository extends JpaRepository<MbtiType, Integer> {

    Optional<MbtiType> findByTypeCode(String typeCode);

    @Query(value = "select typeName from MBTITYPE where typeCode=?1", nativeQuery = true)
    String selectMbtiName(String mbtiType);


}
