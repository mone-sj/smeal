package com.campfire.smeal.service;

import com.campfire.smeal.dto.api.NaverSearchRes;
import com.campfire.smeal.dto.api.Recipe;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class RecipeApiService {

    private final NaverApiService naverApiService;

    private static String key;
    private static String serviceName;

    @Value("${myinfo.recipeApiService.key}")
    public void setKey(String value) {
        key = value;
    }

    @Value("${myinfo.recipeApiService.serviceName}")
    public void setServiceName(String value) {
        serviceName = value;
    }

    public List<Recipe.res> searchRecipe(Recipe.Request recipe,
                                         String how
    ) throws JsonProcessingException {
        // http://openapi.foodsafetykorea.go.kr/api/인증키/서비스명/요청파일타입/요청시작위치/요청종료위치/변수명=값&변수명=값2
        String url = null;

        if (recipe.getRcp_nm() != null) {
            System.out.println("rcp_nm: " + recipe.getRcp_nm());
            url = "http://openapi.foodsafetykorea.go.kr/api/" + key + "/" + serviceName +
                    "/json/" + recipe.getStartIdx() + "/" + recipe.getEndIdx() + "/RCP_NM=" + recipe.getRcp_nm();
        } else if (recipe.getRcp_parts_dtls() != null) {
            System.out.println("getRcp_parts_dtls: " + recipe.getRcp_parts_dtls());
            url = "http://openapi.foodsafetykorea.go.kr/api/" + key + "/" + serviceName +
                    "/json/" + recipe.getStartIdx() + "/" + recipe.getEndIdx() + "/RCP_PARTS_DTLS=" + recipe.getRcp_parts_dtls();
        }

        System.out.println("request_url: " + url);

        RestTemplate restTemplate = new RestTemplate();
        Recipe.Response.Root response = restTemplate.getForObject(url, Recipe.Response.Root.class);

        System.out.println("res");
        System.out.println(response.toString());

        List<String> rcp_nm = new ArrayList();
        List<Recipe.res> result = new ArrayList();
        Recipe.res res = null;

        for (int i = 0; i < response.getCookrcp01().getRow().size(); i++) {
            String rcp_name = response.getCookrcp01().getRow().get(i).getRcp_nm();
            String rcp_way2 = response.getCookrcp01().getRow().get(i).getRcp_way2();
            String att_file_no_main = response.getCookrcp01().getRow().get(i).getAtt_file_no_main();
            rcp_nm.add(rcp_name);

            List itemResultList = new ArrayList();
            System.out.println("레시피 검색 이름: " + rcp_name);
            if (how.equals("blog")) {
                NaverSearchRes.Root naverBlogResult = naverApiService.searchBlog(rcp_name);
                log.info("naverBlogResult");
                System.out.println(naverBlogResult);

                if (naverBlogResult.getDisplay() == 0) {
                    continue;

                } else {
                    for (int j = 0; j < naverBlogResult.getDisplay(); j++) {
                        Recipe.res_item itemResult = Recipe.res_item.builder()
                                .bloggerlink(naverBlogResult.getItems().get(j).getBloggerlink())
                                .bloggername(naverBlogResult.getItems().get(j).getBloggername())
                                .title(naverBlogResult.getItems().get(j).getTitle())
                                .description(naverBlogResult.getItems().get(j).getDescription())
                                .postdate(naverBlogResult.getItems().get(j).getPostdate())
                                .link(naverBlogResult.getItems().get(j).getLink())
                                .build();
//                log.info("itemResult");
//                System.out.println(itemResult);
                        itemResultList.add(itemResult);
                    }

                }

            } else if (how.equals("img")) {
                NaverSearchRes.img.Root naverImgResult = naverApiService.searchImage(rcp_name);
                log.info("naverImgResult");
                System.out.println(naverImgResult);
                if (naverImgResult.getDisplay() == 0) {
                    continue;
                } else {
                    for (int j = 0; j < naverImgResult.getDisplay(); j++) {
                        NaverSearchRes.img.Item itemResult = NaverSearchRes.img.Item.builder()
                                .title(naverImgResult.getItems().get(j).getTitle())
                                .link(naverImgResult.getItems().get(j).getLink())
                                .thumbnail(naverImgResult.getItems().get(j).getThumbnail())
                                .sizeheight(naverImgResult.getItems().get(j).getSizeheight())
                                .sizewidth(naverImgResult.getItems().get(j).getSizeheight())
                                .build();

//                log.info("itemResult");
//                System.out.println(itemResult);
                        itemResultList.add(itemResult);
                    }
                }

            }
            res = Recipe.res.builder()
                    .rcp_nm(rcp_name)
                    .rcp_way2(rcp_way2)
                    .att_file_no_main(att_file_no_main)
                    .res_items(itemResultList)
                    .build();

            result.add(res);
        }

//        System.out.println("rcp_nm 리스트:");
//        System.out.println(rcp_nm);
        return result;
    }

}