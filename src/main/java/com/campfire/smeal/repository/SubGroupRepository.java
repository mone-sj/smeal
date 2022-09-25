package com.campfire.smeal.repository;

import com.campfire.smeal.model.search.SubGroupInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubGroupRepository extends JpaRepository<SubGroupInfo, Integer> {

    @Modifying
    @Query(value = "SELECT * FROM SUBGROUPINFO WHERE mainId=?1", nativeQuery = true)
    List<SubGroupInfo> findSubGroup(int mainId);
}
