package com.campfire.smeal.controller;

import com.campfire.smeal.config.auth.PrincipalDetails;
import com.campfire.smeal.dto.api.Recipe;
import com.campfire.smeal.service.NaverApiService;
import com.campfire.smeal.service.RecipeApiService;
import com.campfire.smeal.service.RecipeSearchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@Controller
public class RecipeSearchController {

    private final RecipeSearchService recipeSearchService;
    private final RecipeApiService recipeApiService;
    private final NaverApiService naverApiService;

    // 재료를 검색하여 음식 리스트 출력하기 위한 페이지
    @GetMapping("/auth/recipeSearchView")
    public String recipeSearchView(Model model) {
        //main group list
        model.addAttribute("mainGroupList",
                recipeSearchService.mainGroupListFindAll());

        return "recipe/searchIngredients";
    }

    // 재료 검색에 따른 식품의약품안전처 음식명 리스트
    @PostMapping("/auth/foodRecipeList")
    public String foodRecipeList(Model model,
                                 @RequestBody(required = false) Recipe.Request request){
        Recipe.Request req =
                new Recipe.Request(request.getRcp_parts_dtls());

        model.addAttribute("searchList",
                recipeApiService.searchFoodList(req));

        return "recipe/searchIngredients :: #resultDiv";
    }

    // 음식으로 검색하기
    @GetMapping("/auth/recipeSearchFood")
    public String searchFood() {
        return "recipe/searchFood";
    }

    // 음식명으로 블로그 검색 결과
    @GetMapping("/auth/nBlogRecipe/{searchFood}")
    public String nBlogRecipe(
            @PathVariable String searchFood,
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            Model model
    ) throws JsonProcessingException {
        System.out.println("요청값 foodName:"+searchFood);
        recipeSearchService.foodNameSave(searchFood, principalDetails);
        model.addAttribute("resultList",
                naverApiService.searchBlog(searchFood));
        return "recipe/searchFood :: #resultDiv";
    }

    // 식품 Mbti 추천 레시피 클릭 이동
    @GetMapping("/auth/recipeSearchFood/{request}")
    public String searchRecommendFood(
            @PathVariable String request,
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            Model model
    )throws JsonProcessingException {

        recipeSearchService.foodNameSave(request, principalDetails);
        model.addAttribute("resultList",
                naverApiService.searchBlog(request));
        return "recipe/searchFood";
    }

    // 추후 삭제 예정
    @GetMapping("/auth/count")
    public @ResponseBody String rankCount() {
        recipeSearchService.rankSave();
        return "완료";
    }

}
