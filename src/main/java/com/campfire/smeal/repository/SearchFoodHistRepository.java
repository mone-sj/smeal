package com.campfire.smeal.repository;

import com.campfire.smeal.dto.FoodRankAllResponseInterface;
import com.campfire.smeal.dto.FoodRankResponseInterface;
import com.campfire.smeal.model.SearchFoodHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SearchFoodHistRepository extends JpaRepository<SearchFoodHistory, Long> {

    @Query(value = "select searchFoodName AS searchFoodName, userFoodMbti AS mbtiType, count(*) AS cnt from searchfoodhistory where DATE(searchDate) between ?1 and ?2\n" +
            "group by searchFoodName, userFoodMbti order by mbtiType asc, cnt desc", nativeQuery = true)
    List<FoodRankResponseInterface> groupByCount(String startDate, String endDate);

    @Query(value = "select searchFoodName AS searchFoodName, count(*) AS cnt from SEARCHFOODHISTORY where DATE(searchDate) between ?1 and ?2\n" +
            "group by searchFoodName order by cnt desc", nativeQuery = true)
    List<FoodRankAllResponseInterface> AllCount(String startDate, String endDate);


}
