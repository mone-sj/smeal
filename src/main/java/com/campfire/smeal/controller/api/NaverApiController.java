package com.campfire.smeal.controller.api;

import com.campfire.smeal.dto.api.NaverCateTrendShoppingReq.CateTrendRequest;
import com.campfire.smeal.dto.api.NaverKeywordTrendShoppingReq.KeywordTrendRequest;
import com.campfire.smeal.dto.api.RecipeRequest;
import com.campfire.smeal.service.NaverApiService;
import com.campfire.smeal.service.RecipeApiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.campfire.smeal.dto.api.NaverCateTrendShoppingReq.toCateTrendRequestDto;
import static com.campfire.smeal.dto.api.NaverKeywordTrendShoppingReq.toKeywordTrendRequestDto;

@RequiredArgsConstructor
@Slf4j
@RestController
public class NaverApiController {

    private final NaverApiService naverApiService;

    private final RecipeApiService recipeApiService;


    @GetMapping("/auth/searchBlog/{searchWord}")
    public String searchBlog(@PathVariable String searchWord, Model model){
        String responseBody = naverApiService.searchBlog(searchWord);
        model.addAttribute("result", responseBody);
        log.info(responseBody);
        return responseBody;
    }

    @PostMapping("/auth/naverTotalSearchTrend")
    public String naverTrend(
            @RequestBody String searchTrend
    ) {
        return naverApiService.datalabSearchTrend(searchTrend);
    }

    @PostMapping("/auth/nShoppingCateTrend")
    public String naverShoppingCateTrend(
            @RequestBody String searchShoppingTrend
    ) throws ParseException, IOException {

        CateTrendRequest cateTrendRequest =
                            toCateTrendRequestDto(searchShoppingTrend);

        String result = naverApiService.cateTrendShopping(
                searchShoppingTrend, cateTrendRequest);

        return result;
    }

    @PostMapping("/auth/nShoppingKeywordTrend")
    public String naverShoppingKeywordTrend(
            @RequestBody String request
    ) throws ParseException, JsonProcessingException {

        KeywordTrendRequest keywordTrendRequest
                = toKeywordTrendRequestDto(request);
        String result = naverApiService.keywordTrendShopping(
                request, keywordTrendRequest);

        return result;

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
