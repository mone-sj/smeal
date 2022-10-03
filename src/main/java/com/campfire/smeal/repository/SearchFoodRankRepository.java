package com.campfire.smeal.repository;

import com.campfire.smeal.dto.searchFood.FoodRankTypeDto;
import com.campfire.smeal.model.SearchFoodRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SearchFoodRankRepository extends JpaRepository<SearchFoodRank, Long> {

    @Modifying
    @Query(value = "INSERT INTO SEARCHFOODRANK(searchKeyword, type, count, rank_, countPeriod, createDate) values(?1,?2,?3,?4,?5, now())", nativeQuery = true)
    int msave(String searchKeyword, String mbtiType, String count, int rank, String countPeriod);


    @Query(value = "select searchKeyword, count, rank_, countPeriod from searchfoodrank where type='All' and DATE(createDate) in (select max(DATE(createDate)) from searchfoodrank group by createDate) order by rank_", nativeQuery = true)
    List<FoodRankTypeDto> typeAll();

    @Query(value = "select searchKeyword, count, rank_, countPeriod from searchfoodrank where type=?1 and DATE(createDate) in (select max(DATE(createDate)) from searchfoodrank group by createDate) order by rank_", nativeQuery = true)
    List<FoodRankTypeDto> typeRank(String type);

    @Query(value = "select searchKeyword, count, rank_, countPeriod from searchfoodrank where type='All' and DATE(createDate) in (select max(DATE(createDate)) from searchfoodrank group by createDate) order by rank_ limit 3", nativeQuery = true)
    List<FoodRankTypeDto> AllTop3();


}
