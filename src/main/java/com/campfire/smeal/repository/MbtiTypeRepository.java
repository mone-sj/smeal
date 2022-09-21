package com.campfire.smeal.repository;

import com.campfire.smeal.model.mbti.MbtiType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MbtiTypeRepository extends JpaRepository<MbtiType, Integer> {

    Optional<MbtiType> findByTypeCode(String typeCode);
}
