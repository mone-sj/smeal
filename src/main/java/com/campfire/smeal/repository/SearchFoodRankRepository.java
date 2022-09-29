package com.campfire.smeal.repository;

import com.campfire.smeal.model.SearchFoodRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SearchFoodRankRepository extends JpaRepository<SearchFoodRank, Long> {

    @Modifying
    @Query(value = "INSERT INTO SEARCHFOODRANK(searchKeyword, type, count, rank, createDate) values(?1,?2,?3,?4, now())", nativeQuery = true)
    int msave(String searchKeyword, String mbtiType, String count, int rank);


}
