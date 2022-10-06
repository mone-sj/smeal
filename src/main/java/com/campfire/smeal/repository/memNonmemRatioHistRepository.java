package com.campfire.smeal.repository;

import com.campfire.smeal.model.memNonmemRatioHist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface memNonmemRatioHistRepository extends JpaRepository<memNonmemRatioHist, Integer> {

    @Query(value = "select * from memnonmemratiohist order by createDate desc limit 1", nativeQuery = true)
    memNonmemRatioHist memStatusRatio();
}
