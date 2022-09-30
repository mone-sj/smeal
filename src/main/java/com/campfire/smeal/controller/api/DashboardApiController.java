package com.campfire.smeal.controller.api;

import com.campfire.smeal.dto.ResponseDto;
import com.campfire.smeal.service.RecipeSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class DashboardApiController {

    private final RecipeSearchService recipeSearchService;

    // 삭제해도 될듯..
    @GetMapping("/auth/typeAllRank")
    public @ResponseBody ResponseDto<Integer> mbtiTypeAllRankList() {
        recipeSearchService.searchFoodRankList();
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    // 삭제해도 될듯..
    @GetMapping("/auth/typeRank")
    public @ResponseBody ResponseDto<Integer> mbtiTypeRankList() {
        recipeSearchService.searchFoodTypeRankList("D");
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
}
