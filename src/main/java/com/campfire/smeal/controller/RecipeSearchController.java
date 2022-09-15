package com.campfire.smeal.controller;

import com.campfire.smeal.dto.api.Recipe;
import com.campfire.smeal.service.RecipeApiService;
import com.campfire.smeal.service.RecipeSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Slf4j
@Controller
public class RecipeSearchController {

    private final RecipeSearchService recipeSearchService;
    private final RecipeApiService recipeApiService;

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

}
