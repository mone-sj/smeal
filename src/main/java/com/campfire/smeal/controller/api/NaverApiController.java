package com.campfire.smeal.controller.api;

import com.campfire.smeal.dto.ResponseDto;
import com.campfire.smeal.dto.api.*;
import com.campfire.smeal.dto.api.NaverApiTrendShoppingRes.NaverKeywordTrendShoppingRes.AllKeywordResponse;
import com.campfire.smeal.dto.api.NaverCateTrendShoppingReq.CateTrendRequest;
import com.campfire.smeal.dto.api.NaverSearchRes.Item;
import com.campfire.smeal.model.search.SubGroupInfo;
import com.campfire.smeal.service.NaverApiService;
import com.campfire.smeal.service.RecipeApiService;
import com.campfire.smeal.service.RecipeSearchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.campfire.smeal.dto.api.NaverCateTrendShoppingReq.toCateTrendRequestDto;

@RequiredArgsConstructor
@Slf4j
@RestController
public class NaverApiController {

    private final NaverApiService naverApiService;

    private final RecipeApiService recipeApiService;
    private final RecipeSearchService recipeSearchService;

    // 네이버 키워드별 트렌드 조회 - 추후 DB 조회 기능 추가하면 삭제 예정
    @PostMapping("/auth/nShoppingKeywordTrend")
    public ResponseDto<AllKeywordResponse> naverShoppingKeywordTrend(
            @RequestBody String request
    ) throws ParseException, JsonProcessingException {

//        String result = naverApiService.keywordTrendShopping(request);
        AllKeywordResponse result= naverApiService.keywordTrendShopping(request);

        return new ResponseDto<AllKeywordResponse>(HttpStatus.OK.value(),result);
    }

    // 네이버 키워드별 트렌드 조회-DB에서 조회하여 트렌드 출력
    @PostMapping("/auth/nShoppingKeywordTrendStatistics")
    public String naverShoppingKeywordTrend()
            throws ParseException, JsonProcessingException {

        // DB에서 검색많이한 음식 조회해서 키워드 트렌드 조회하기
        // DB 조회 코드 삽입
        List<String> paramList = new ArrayList<>();
        //String result = naverApiService.keywordTrendShopping(paramList);
        return null;
    }

    // 재료 검색을 위한 소분류(SubGroupList) 리스트 조회 (완)
    @GetMapping("/auth/mainGroup/{groupId}")
    public ResponseDto<List<SubGroupInfo>> subGroupList(
            @PathVariable String groupId) {
        List<SubGroupInfo> result =
                recipeSearchService.subGroupInfoList(Integer.parseInt(groupId));
        return new ResponseDto<List<SubGroupInfo>>
                (HttpStatus.OK.value(), result);
    }

    /*음식명을 네이버 블로그 검색 하여 결과 리턴 ->
    RestController가 아닌 controller로 modelAttribute로 보내야할 듯*/
//    @GetMapping("/auth/nBlogRecipe/{searchFood}")
//    public ResponseDto<ArrayList<Item>> nBlogRecipe(
//            @PathVariable String searchFood
//    ) throws JsonProcessingException {
//        System.out.println("요청값 foodName:"+searchFood);
//        NaverSearchRes.Root searchBlogResult =
//                naverApiService.searchBlog(searchFood);
//
//        return new ResponseDto<ArrayList<Item>>(
//                HttpStatus.OK.value(), searchBlogResult.getItems());
//    }

    @PostMapping("/auth/nBlogRecipePost")
    public ResponseDto<ArrayList<Item>> nBlogRecipePost(
            @RequestParam String foodName
    ) throws JsonProcessingException {
        System.out.println("요청값 foodName:"+foodName);
        NaverSearchRes.Root searchBlogResult =
                naverApiService.searchBlog(foodName);

        return new ResponseDto<ArrayList<Item>>(
                HttpStatus.OK.value(), searchBlogResult.getItems());
    }

    // 레시피 api - 식품의약품안전처 -> 네이버 블로그 검색 (추후 사용 안할 것 같음)
    @PostMapping("/auth/recipe/blog")
    public ResponseDto<List<Recipe.res>> recipeBlog(
            @RequestBody Recipe.Request request
    ) throws JsonProcessingException {
        Recipe.Request req =
                new Recipe.Request(request.getRcp_nm(), request.getRcp_parts_dtls());
        System.out.println(req);
        List<Recipe.res> responseBody = recipeApiService.searchRecipe(req, "blog");
        System.out.println(responseBody);

        return new ResponseDto<List<Recipe.res>>(HttpStatus.OK.value(),responseBody);
    }

    /*
                                    아래는 사용하지 않음
    */

    // 네이버 분야별 트렌드 조회
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

    // 네이버 통합검색어 트랜드
    @PostMapping("/auth/naverTotalSearchTrend")
    public String naverTrend(
            @RequestBody String searchTrend
    ) {
        return naverApiService.datalabSearchTrend(searchTrend);
    }


    // 식약처에서 음식명 찾은 후 네이버 블로그 이미지 검색
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
