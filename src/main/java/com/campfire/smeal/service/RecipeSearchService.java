package com.campfire.smeal.service;

import com.campfire.smeal.config.auth.PrincipalDetails;
import com.campfire.smeal.dto.FoodRankAllResponseInterface;
import com.campfire.smeal.dto.FoodRankResponseInterface;
import com.campfire.smeal.handler.exception.GeneralException;
import com.campfire.smeal.handler.exception.SmErrorCode;
import com.campfire.smeal.model.SearchFoodHistory;
import com.campfire.smeal.model.User;
import com.campfire.smeal.model.search.MainGroupInfo;
import com.campfire.smeal.model.search.SubGroupInfo;
import com.campfire.smeal.repository.SearchFoodHistRepository;
import com.campfire.smeal.repository.SearchFoodRankRepository;
import com.campfire.smeal.repository.SubGroupRepository;
import com.campfire.smeal.repository.MainGroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class RecipeSearchService {

    private final MainGroupRepository mainGroupRepository;
    private final SubGroupRepository subGroupRepository;
    private final SearchFoodHistRepository searchFoodHistRepository;
    private final SearchFoodRankRepository searchFoodRankRepository;

//    @Transactional
//    public Map<String, Object> cateListFindAll() {
//        Map<String, Object> result = new HashMap<>();
//        List<MainGroupInfo> mainGroupInfoList = mainGroupRepository.findAll();
//        System.out.println("mainGroupInfoList");
//        System.out.println(mainGroupInfoList);
//        result.put("mainGroupList", mainGroupInfoList);
//
//        List<SubGroupInfo> subGroupInfoList = subGroupRepository.findAll();
//        result.put("subCateList", mainGroupRepository.findAll());
//        return result;
//    }

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

    @Transactional
    public void rankSave() {
        // count
        List<FoodRankResponseInterface> foodRankDtos =
                searchFoodHistRepository.groupByCount("2022-09-26", "2022-09-30");

        System.out.println("groupby rank 결과: ");
        for (int i = 0; i < foodRankDtos.size(); i++) {
            System.out.println(foodRankDtos.get(i).getSearchFoodName());
            System.out.println(foodRankDtos.get(i).getMbtiType());
            System.out.println(foodRankDtos.get(i).getCnt());

            if (foodRankDtos.get(i).getMbtiType() == null) {

            }
        }


        List<FoodRankAllResponseInterface> foodRankAllDto =
                searchFoodHistRepository.AllCount("2022-09-26", "2022-09-30");

        System.out.println("all count 결과");
        for (int i = 0; i < foodRankAllDto.size(); i++) {
            System.out.println(foodRankAllDto.get(i).getSearchFoodName());
            System.out.println(foodRankAllDto.get(i).getCnt());
        }


        // searchFoodRank Table save
        int rank = 0;
        for (int i = 0; i < foodRankDtos.size(); i++) {
            System.out.println("1");
            if (i == 0) {
                System.out.println("2");
            } else if (foodRankDtos.get(i).getSearchFoodName().equals(foodRankDtos.get(i - 1).getMbtiType())) {
                if (!foodRankDtos.get(i).getMbtiType().equals(foodRankDtos.get(i - 1).getMbtiType())) {
                    rank = 0;
                    System.out.println("3");
                }
            } else if (!foodRankDtos.get(i).getSearchFoodName().equals(foodRankDtos.get(i - 1).getMbtiType())) {
                rank = 0;
            }
            System.out.println("4");
            searchFoodRankRepository.msave(foodRankDtos.get(i).getSearchFoodName(),
                    foodRankDtos.get(i).getMbtiType(),
                    String.valueOf(foodRankDtos.get(i).getCnt()),
                    rank+1);
            System.out.println("5");
        }
        System.out.println("type별 저장 완료");

        for (int i = 0; i < foodRankAllDto.size(); i++) {
            searchFoodRankRepository.msave(foodRankAllDto.get(i).getSearchFoodName(),
                    "All",
                    String.valueOf(foodRankAllDto.get(i).getCnt()),
                    i+1);
        }
        System.out.println("전체 저장 완료");





    }
}
