package com.campfire.smeal.controller;

import com.campfire.smeal.dto.ResponseDto;
import com.campfire.smeal.dto.api.NaverSearchRes;
import com.campfire.smeal.dto.api.Recipe;
import com.campfire.smeal.service.NaverApiService;
import com.campfire.smeal.service.RecipeApiService;
import com.campfire.smeal.service.RecipeSearchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

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
//        if (request == null) {
//            // DB 조회
//        }
        System.out.println("request");
        System.out.println(request);
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
            Model model
    ) throws JsonProcessingException {
        System.out.println("요청값 foodName:"+searchFood);
        NaverSearchRes.Root searchBlogResult =
                naverApiService.searchBlog(searchFood);
        System.out.println("searchBlogResult.getItems()");
        System.out.println(searchBlogResult.getItems());
        System.out.println("searchBlogResult.getDisplay(): " + searchBlogResult.getDisplay());
        System.out.println("searchBlogResult.getTotal(): " + searchBlogResult.getTotal());


        model.addAttribute("resultList",
                searchBlogResult);
        return "recipe/searchFood :: #resultDiv";
    }

}
