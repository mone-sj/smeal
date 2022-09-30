package com.campfire.smeal.service;

import com.campfire.smeal.config.auth.PrincipalDetails;
import com.campfire.smeal.dto.api.NaverKeywordTrendShoppingReq;
import com.campfire.smeal.dto.api.NaverKeywordTrendShoppingReq.KeywordTrendRequest;
import com.campfire.smeal.dto.searchFood.FoodRankAllResponseInterface;
import com.campfire.smeal.dto.searchFood.FoodRankResponseInterface;
import com.campfire.smeal.dto.searchFood.FoodRankTypeDto;
import com.campfire.smeal.model.SearchFoodHistory;
import com.campfire.smeal.model.User;
import com.campfire.smeal.model.search.MainGroupInfo;
import com.campfire.smeal.model.search.SubGroupInfo;
import com.campfire.smeal.repository.MainGroupRepository;
import com.campfire.smeal.repository.SearchFoodHistRepository;
import com.campfire.smeal.repository.SearchFoodRankRepository;
import com.campfire.smeal.repository.SubGroupRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.campfire.smeal.dto.api.NaverKeywordTrendShoppingReq.keywordTrendRequestDefaultDbDto;

@Slf4j
@RequiredArgsConstructor
@Service
public class RecipeSearchService {

    private final MainGroupRepository mainGroupRepository;
    private final SubGroupRepository subGroupRepository;
    private final SearchFoodHistRepository searchFoodHistRepository;
    private final SearchFoodRankRepository searchFoodRankRepository;

    @Transactional
    public List<MainGroupInfo> mainGroupListFindAll() {
        return mainGroupRepository.findAll();
    }

    @Transactional
    public List<SubGroupInfo> subGroupInfoList(int groupId){
        List<SubGroupInfo> subGroupInfoList = subGroupRepository.findSubGroup(groupId);
        System.out.println("subGroupInfoList");
        System.out.println(subGroupInfoList);
        return subGroupInfoList;
    }

    @Transactional
    public void foodNameSave(String foodName,
                             PrincipalDetails principalDetails) {
        User user = null;
        if (principalDetails != null) {
            user = principalDetails.getUser();
        }

        SearchFoodHistory searchFood = SearchFoodHistory.builder()
                .searchFoodName(foodName)
                .user(user)
                .build();
        searchFoodHistRepository.save(searchFood);
    }

    // 월요일마다 실행
    @Scheduled(cron="* * * * * 1")
    @Transactional
    public void rankSave() {

//        String startDate = "2022-09-26";
//        String endDate = "2022-09-30";
        // countPeriod 작성
        Map<String, String> period = getCountPeriod();
        String startDate = period.get("startDate");
        String endDate = period.get("endDate");

        String countPeriod = startDate + " ~ " + endDate;

        // count
        List<FoodRankResponseInterface> foodRankDtos =
                searchFoodHistRepository.groupByCount(startDate, endDate);
        System.out.println("groupby rank count 완");


        List<FoodRankAllResponseInterface> foodRankAllDto =
                searchFoodHistRepository.AllCount(startDate, endDate);
        System.out.println("all count 완");

        // searchFoodRank Table save
        int rank = 0;
        for (int i = 0; i < foodRankDtos.size(); i++) {
            rank += 1;
            if (i == 0 | foodRankDtos.get(i).getMbtiType() == null) {
                searchFoodRankRepository.msave(foodRankDtos.get(i).getSearchFoodName(),
                        "NoneType",
                        String.valueOf(foodRankDtos.get(i).getCnt()),
                        rank,
                        countPeriod);
            } else if (!foodRankDtos.get(i).getMbtiType().equals(foodRankDtos.get(i - 1).getMbtiType())) {
                rank = 1;
                searchFoodRankRepository.msave(foodRankDtos.get(i).getSearchFoodName(),
                        foodRankDtos.get(i).getMbtiType(),
                        String.valueOf(foodRankDtos.get(i).getCnt()),
                        rank,
                        countPeriod);
            } else if (foodRankDtos.get(i).getMbtiType().equals(foodRankDtos.get(i - 1).getMbtiType())) {
                searchFoodRankRepository.msave(foodRankDtos.get(i).getSearchFoodName(),
                        foodRankDtos.get(i).getMbtiType(),
                        String.valueOf(foodRankDtos.get(i).getCnt()),
                        rank,
                        countPeriod);
            }
        }
        System.out.println("type별 저장 완료");

        for (int i = 0; i < foodRankAllDto.size(); i++) {
            searchFoodRankRepository.msave(foodRankAllDto.get(i).getSearchFoodName(),
                    "All",
                    String.valueOf(foodRankAllDto.get(i).getCnt()),
                    i+1,
                    countPeriod);
        }
        System.out.println("전체 저장 완료");
    }

    public Map<String, String> getCountPeriod() {
        // 지난 주 월요일 ~ 일요일까지의 날짜 구하기
        HashMap<String, String> period = new HashMap<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal = Calendar.getInstance();
        System.out.println("오늘날짜: " + formatter.format(cal.getTime()));

        // 지난 주 일요일:
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        String endDate = formatter.format(cal.getTime());
        period.put("endDate", endDate);

        // 지난 주 월요일:
        cal.add(Calendar.DATE, -7);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        String startDate = formatter.format(cal.getTime());
        period.put("startDate", startDate);

        return period;
    }

    // searchFoodRank 에서 전체 검색어 기준
    // 최근 일주일 최다 검색어 상위 3위 list 가져오기
    @Transactional
    public KeywordTrendRequest searchFoodAllRankTop3() throws JsonProcessingException {
        System.out.println("전체 최다 검색어 3위");
        List<FoodRankTypeDto> foodRankAllDtos =
                searchFoodRankRepository.AllTop3();
        List<String> top3FoodList = new ArrayList<>();
        for (int i = 0; i < foodRankAllDtos.size(); i++) {
            if (foodRankAllDtos.get(i).getSearchKeyword() == null) {
                break;
            } else {
                top3FoodList.add(foodRankAllDtos.get(i).getSearchKeyword());
            }
        }
        return keywordTrendRequestDefaultDbDto(top3FoodList);
    }


    // searchFoodRank 에서 최근 일주일 최다 검색어 list 가져오기(전체) - 삭제해도 될듯
    @Transactional
    public void searchFoodRankList() {
        System.out.println("전체 최다 검색어");
        List<FoodRankTypeDto> foodRankAllDtos = searchFoodRankRepository.typeAll();
        for (int i = 0; i < foodRankAllDtos.size(); i++) {
            System.out.println(foodRankAllDtos.get(i).getSearchKeyword());
            System.out.println(foodRankAllDtos.get(i).getCount());
            System.out.println(foodRankAllDtos.get(i).getRank_());
            System.out.println(foodRankAllDtos.get(i).getCountPeriod());
        }
    }

    // searchFoodRank 에서 최근 일주일 최다 검색어 list 가져오기(전체) - 삭제해도 될듯
    @Transactional
    public void searchFoodTypeRankList(String type) {
        System.out.println("타입별 최다 검색어- type: " + type);
        List<FoodRankTypeDto> foodRankTypeDtos = searchFoodRankRepository.typeRank(type);
        for (int i = 0; i < foodRankTypeDtos.size(); i++) {
            System.out.println(foodRankTypeDtos.get(i).getSearchKeyword());
            System.out.println(foodRankTypeDtos.get(i).getCount());
            System.out.println(foodRankTypeDtos.get(i).getRank_());
            System.out.println(foodRankTypeDtos.get(i).getCountPeriod());
        }
    }


}
