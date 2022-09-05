package com.campfire.smeal.controller.api;

import com.campfire.smeal.dto.ResponseDto;
import com.campfire.smeal.dto.api.NaverCateTrendShoppingReq.CateTrendRequest;
import com.campfire.smeal.dto.api.NaverKeywordTrendShoppingReq.KeywordTrendRequest;
import com.campfire.smeal.dto.api.NaverSearchRes;
import com.campfire.smeal.dto.api.Recipe;
import com.campfire.smeal.service.NaverApiService;
import com.campfire.smeal.service.RecipeApiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static com.campfire.smeal.dto.api.NaverCateTrendShoppingReq.toCateTrendRequestDto;
import static com.campfire.smeal.dto.api.NaverKeywordTrendShoppingReq.toKeywordTrendRequestDto;

@RequiredArgsConstructor
@Slf4j
@RestController
public class NaverApiController {

    private final NaverApiService naverApiService;

    private final RecipeApiService recipeApiService;


    // 네이버 블로그 검색
    @GetMapping("/auth/searchBlog/{searchWord}")
    public NaverSearchRes.Root searchBlog(@PathVariable String searchWord, Model model) throws JsonProcessingException {
        NaverSearchRes.Root responseBody = naverApiService.searchBlog(searchWord);
        model.addAttribute("result", responseBody);
        log.info("responseBody-NaverApiController");
        System.out.println(responseBody);
        return responseBody;
    }

    // 네이버 이미지 검색
    @GetMapping("/auth/searchImage/{searchWord}")
    public String searchImage(@PathVariable String searchWord, Model model){
        String responseBody = naverApiService.searchImageOrigin(searchWord);
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

        // convert : request(String) -> object
        CateTrendRequest cateTrendRequest =
                            toCateTrendRequestDto(searchShoppingTrend);

        String result = naverApiService.cateTrendShopping(
                searchShoppingTrend, cateTrendRequest);

        return result;
    }

//    @PostMapping("/auth/nShoppingCateTrendTest")
//    public String naverShoppingCateTrendTest(
//            @RequestBody String searchShoppingTrend
//    ) throws ParseException, IOException {
//
//        // convert : request(String) -> object
//        CateTrendRequest cateTrendRequest =
//                toCateTrendRequestDto(searchShoppingTrend);
//
//        String result = naverApiService.cateTrendShopping(
//                searchShoppingTrend, cateTrendRequest);
//
//        return result;
//    }

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

    // 레시피 api - 식품의약품안전처 (네이버 블로그 검색)
    @PostMapping("/auth/recipe/blog")
    public ResponseDto<List<Recipe.res>> recipeBlog(@RequestBody Recipe.Request request
            ) throws JsonProcessingException {
        Recipe.Request req =
                new Recipe.Request(request.getRcp_nm(), request.getRcp_parts_dtls());
        System.out.println(req);
        List<Recipe.res> responseBody = recipeApiService.searchRecipe(req, "blog");
        System.out.println(responseBody);

        return new ResponseDto<List<Recipe.res>>(HttpStatus.OK.value(),responseBody);
    }

    @PostMapping("/auth/recipe/img")
    public ResponseDto<List<Recipe.res>> recipeImage(@RequestBody Recipe.Request request
    ) throws JsonProcessingException {
        Recipe.Request req =
                new Recipe.Request(request.getRcp_nm(), request.getRcp_parts_dtls());
        System.out.println(req);
        List<Recipe.res> responseBody = recipeApiService.searchRecipe(req, "img");
        System.out.println(responseBody);

        return new ResponseDto<List<Recipe.res>>(HttpStatus.OK.value(),responseBody);
    }

}
