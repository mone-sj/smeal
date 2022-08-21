package com.campfire.smeal.controller.api;

import com.campfire.smeal.dto.NaverSearchTrendShopping;
import com.campfire.smeal.dto.NaverTotalSearchTrend;
import com.campfire.smeal.dto.RecipeRequest;
import com.campfire.smeal.dto.ResponseDto;
import com.campfire.smeal.service.NaverApiService;
import com.campfire.smeal.service.RecipeApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RequiredArgsConstructor
@Slf4j
@RestController
public class NaverApiController {

    private final NaverApiService.NaverSearchBlog naverSearchBlog;
    private final NaverApiService.naverDatalabTotalSearchTrend naverSearchTrend;

    private final RecipeApiService recipeApiService;


    @GetMapping("/auth/searchBlog/{searchWord}")
    public String searchBlog(@PathVariable String searchWord, Model model){
        String responseBody = naverSearchBlog.searchBlog(searchWord);
        model.addAttribute("result", responseBody);
        log.info(responseBody);
        return responseBody;
    }

    @PostMapping("/auth/naverTotalSearchTrend")
    public String naverTrend(
            @RequestBody NaverTotalSearchTrend searchTrend
    ) {
        System.out.println("NaverApiController_requestBody searchTrend 객체로 출력");
        System.out.println(searchTrend);
        String responseBody = naverSearchTrend.DatalabSearchTrend(searchTrend);
        log.info(responseBody);
        return responseBody;
    }

    // 레시피 api - 식품의약품안전처
    @PostMapping("/auth/recipe")
    public String recipe(
            @RequestBody RecipeRequest recipeRequest
            ) {
        String responseBody = recipeApiService.searchRecipe(recipeRequest);
        log.info(responseBody);
        return responseBody;
    }


}
