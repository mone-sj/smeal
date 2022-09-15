package com.campfire.smeal.service;

import com.campfire.smeal.dto.api.NaverApiTrendShoppingRes;
import com.campfire.smeal.dto.api.NaverApiTrendShoppingRes.NaverClickTrendShoppingRes.ApiTrendResDto;
import com.campfire.smeal.dto.api.NaverApiTrendShoppingRes.NaverKeywordTrendShoppingRes.*;
import com.campfire.smeal.dto.api.NaverApiTrendShoppingTargetRes;
import com.campfire.smeal.dto.api.NaverApiTrendShoppingTargetRes.KeywordTargetTrendShoppingRes.AgeRes;
import com.campfire.smeal.dto.api.NaverApiTrendShoppingTargetRes.KeywordTargetTrendShoppingRes.ApiKeywordTrendTargetResDto;
import com.campfire.smeal.dto.api.NaverApiTrendShoppingTargetRes.KeywordTargetTrendShoppingRes.GenderRes;
import com.campfire.smeal.dto.api.NaverApiTrendShoppingTargetRes.KeywordTargetTrendShoppingRes.KeywordTargetRearrRes;
import com.campfire.smeal.dto.api.NaverCateTrendShoppingReq.CateTrendRequest;
import com.campfire.smeal.dto.api.NaverCateTrendShoppingReq.CategoryRequest;
import com.campfire.smeal.dto.api.NaverKeywordTrendShoppingReq.KeywordTrendRequest;
import com.campfire.smeal.dto.api.NaverSearchRes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static com.campfire.smeal.dto.api.NaverKeywordTrendShoppingReq.keywordTrendRequestDefaultDto;

@Slf4j
@Service
public class NaverApiService {

    private static String clientId;
    private static String clientSecret;

    @Value("${myinfo.naverApi.naverClientId}")
    public void setClientId(String value) {
        clientId = value;
    }

    @Value("${myinfo.naverApi.naverClientSecret}")
    public void setClientSecret(String value) {
        clientSecret = value;
    }

    private static Map<String, String> requestHeaders() {
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        requestHeaders.put("Content-Type", "application/json");
        return requestHeaders;
    }

    // 블로그 검색 (재료에 따른 레시피 검색)
    // https://developers.naver.com/docs/serviceapi/search/blog/blog.md#%EB%B8%94%EB%A1%9C%EA%B7%B8
    public NaverSearchRes.Root searchBlog(String searchWord)
            throws JsonProcessingException {
        String text = null;

        try {
            text = URLEncoder.encode(searchWord, "UTF-8");
            System.out.println("검색어: " + text);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패", e);
        }

        String searchBlog_apiURL =
                "https://openapi.naver.com/v1/search/blog?query=" + text + "&display=100";    // json 결과

        String responseBody = get(searchBlog_apiURL, requestHeaders());

        ObjectMapper mapper = new ObjectMapper();
        NaverSearchRes.Root res = mapper.readValue(
                responseBody, NaverSearchRes.Root.class);

//        log.info("res");
//        System.out.println(res);

        return res;

    }

    /*네이버 검색-이미지*/
    public NaverSearchRes.img.Root searchImage(String searchWord) throws JsonProcessingException {

        String text = null;
        try {
            text = URLEncoder.encode(searchWord, "UTF-8");
            System.out.println("검색어: " + text);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패", e);
        }

        String searchImage_apiURL =
                "https://openapi.naver.com/v1/search/image?query=" + text;    // json 결과

        String responseBody = get(searchImage_apiURL, requestHeaders());
        System.out.println(responseBody);

        ObjectMapper mapper = new ObjectMapper();
        NaverSearchRes.img.Root res = mapper.readValue(
                responseBody, NaverSearchRes.img.Root.class);
        return res;

    }

    // 네이버 쇼핑인사이트 키워드별 트렌드 조회
//    public String keywordTrendShopping(String req
    public AllKeywordResponse keywordTrendShopping(String req
    ) throws ParseException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        // 매개변수 req의 초기 객체화
        KeywordTrendRequest keywordTrendRequest =
                keywordTrendRequestDefaultDto(req);

        String request =
                mapper.writeValueAsString(keywordTrendRequest);

        // 초기화
        // 키워드 리스트 : keywordList
        // target Url : urlMap
        List<String> keywordList = new ArrayList<>();
        Map<String, String> urlMap = new HashMap<>();
        urlMap.put("gender", "https://openapi.naver.com/v1/datalab/shopping/category/keyword/gender");
        urlMap.put("age", "https://openapi.naver.com/v1/datalab/shopping/category/keyword/age");

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj = (JSONObject) jsonParser.parse(request);

        // 쇼핑 검색어별 트랜드(클릭량 추이)
        String dataLabTrendShopping_keyword
                = "https://openapi.naver.com/v1/datalab/shopping/category/keywords";
        String responseBodyTotal = post(dataLabTrendShopping_keyword,
                requestHeaders(), request);

        // naver responseBodyTotal 객체화
        ApiKeywordTrendResDto keywordTrendResDto =
                mapper.readValue(
                        responseBodyTotal,
                        ApiKeywordTrendResDto.class);

        // keyword별 ratio (clickTrend) 통합 계산
        Map<String, Double> result = new HashMap<>();
        for (int i = 0; i < keywordTrendResDto.getResults().size(); i++) {
            Results keywordResult = keywordTrendResDto.getResults().get(i);
            Double ratioSum = 0.0;
            for (int j = 0; j < keywordResult.getData().size(); j++) {
                Double ratio =
                        Double.valueOf(keywordResult.getData().get(j).getRatio());
                ratioSum += ratio;
            }
            Double ratioAverage = ratioSum / keywordResult.getData().size();
            result.put(keywordResult.getKeyword().get(0), ratioAverage);
        } //for문 끝

        Double divisionBytotalRatioSum =
                100 / result.values().stream().mapToDouble(Double::doubleValue).sum();

        List<TotalClick> totalClickList = new ArrayList<>();
        // clickTrend 계산
        for (String key : result.keySet()) {
            result.put(key, result.get(key) * divisionBytotalRatioSum);
            keywordList.add(key);
            TotalClick totalClick =
                    TotalClick.builder()
                            .keyword(key)
                            .ratio(String.format("%.2f",result.get(key)))
                            .build();
            totalClickList.add(totalClick);
        }

        // 연령별, 성별 (target) 결과 list 초기화
        List<AgeRes> ageResList = new ArrayList<>();
        List<GenderRes> genderResList = new ArrayList<>();

        // 키워드별 연령별, 성별 결과값 받아오기
        for (String keyword : keywordList) {
            for (String urlMapKey : urlMap.keySet()) {
                jsonObj.replace("keyword", keyword);
                String responseBody=post(urlMap.get(urlMapKey), requestHeaders(),
                        jsonObj.toString());

                ApiKeywordTrendTargetResDto keywordTargetTrend =
                        mapper.readValue(
                                responseBody,
                                ApiKeywordTrendTargetResDto.class);

                ArrayList<KeywordTargetRearrRes> KeywordTargetRearrResList = new ArrayList<>();
                for (int j = 0; j < keywordTargetTrend.getResults().size(); j++) {
                    List<NaverApiTrendShoppingTargetRes.KeywordTargetTrendShoppingRes.ResultData>
                            targetResultResList = keywordTargetTrend.getResults().get(j).getData();

                    // group 명 찾기
                    List<String> groupList = new ArrayList<>();
                    for (NaverApiTrendShoppingTargetRes.KeywordTargetTrendShoppingRes.ResultData resultData : targetResultResList) {
                        groupList.add(resultData.getGroup());
                    }
                    // 중복제거
                    List<String> newGroupList = groupList.stream().distinct().collect(Collectors.toList());

                    // group별 ratio 평균값 계산
                    Map<String, Double> targetResultMap = new HashMap<>();
                    for (String group : newGroupList) {
                        Double targetRatioSum=0.0;
                        for (int k = 0; k < targetResultResList.size(); k++) {
                            if (targetResultResList.get(k).getGroup().equals(group)) {
                                Double targetRatio = Double.valueOf(targetResultResList.get(k).getRatio());
                                targetRatioSum += targetRatio;
                            }
                        }
                        Double targetRatioAvg = targetRatioSum / (targetResultResList.size() / newGroupList.size());
                        targetResultMap.put(group, targetRatioAvg);
                    }
                    Double divisionByTargetRatioSum =
                            100 / targetResultMap.values().stream().mapToDouble(Double::doubleValue).sum();

                    // targetResultMap= group:ratio  -> targetRearrRes 객체화
                    for (String key : targetResultMap.keySet()) {
                        targetResultMap.put(key, targetResultMap.get(key) * divisionByTargetRatioSum);
                        KeywordTargetRearrRes keywordTargetRearrRes =
                                KeywordTargetRearrRes.builder()
                                        .group(key)
                                        .ratio(String.format("%.2f",targetResultMap.get(key)))
                                        .build();
                        KeywordTargetRearrResList.add(keywordTargetRearrRes);
                    }
                }

                if (urlMapKey.equals("age")) {
                    AgeRes ageRes =
                            AgeRes.builder()
                                    .keyword(keyword)
                                    .results(KeywordTargetRearrResList)
                                    .build();
                    ageResList.add(ageRes);
                } else if (urlMapKey.equals("gender")) {
                    GenderRes genderRes =
                            GenderRes.builder()
                                    .keyword(keyword)
                                    .results(KeywordTargetRearrResList)
                                    .build();
                    genderResList.add(genderRes);
                }
            }
        }
        AllKeywordResponse allKeywordResponse=
                AllKeywordResponse.builder()
                        .totalClick(totalClickList)
                        .ages(ageResList)
                        .genders(genderResList)
                        .build();

        String responseJson = mapper.writeValueAsString(allKeywordResponse);
        log.info("responseJson");
        System.out.println(responseJson);

        //return responseJson;
        return allKeywordResponse;
    }


    public String searchImageOrigin(String searchWord) {

        String text = null;
        try {
            text = URLEncoder.encode(searchWord, "UTF-8");
            System.out.println("검색어: " + text);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패", e);
        }

        String searchImage_apiURL =
                "https://openapi.naver.com/v1/search/image?query=" + text;    // json 결과

        String responseBody = get(searchImage_apiURL, requestHeaders());
        System.out.println(responseBody);
        return responseBody;
    }

    // 네이버 통합 검색어 트렌드 API
    // https://developers.naver.com/docs/serviceapi/datalab/search/search.md#%ED%86%B5%ED%95%A9-%EA%B2%80%EC%83%89%EC%96%B4-%ED%8A%B8%EB%A0%8C%EB%93%9C
    public String datalabSearchTrend(String searchTrendRequest) {
        String apiUrl = "https://openapi.naver.com/v1/datalab/search";

        String responseBody = post(apiUrl, requestHeaders(), searchTrendRequest);
        System.out.println(responseBody);
        return responseBody;
    }

    // 네이버 쇼핑인사이트 - 분야별 트렌드 조회
    public String cateTrendShopping(String searchTrendShopping,
                                    CateTrendRequest cateTrendRequest
    ) throws ParseException, JsonProcessingException {
        // searchTrendShopping, cateTrendRequest 같은 값. 자료형만 다름

        // 초기화
        // 카테고리 리스트 : paramList
        // url 리스트: urlList
        // target 리스트(기기,성,연령별) : resultList
        List<String> cateList = new ArrayList<>();
        List<String> urlList = Arrays.asList(
                //"https://openapi.naver.com/v1/datalab/shopping/category/device", // 기기별
                "https://openapi.naver.com/v1/datalab/shopping/category/gender", // 성별
                "https://openapi.naver.com/v1/datalab/shopping/category/age"); // 연령별
        List<String> resultList = Arrays.asList("gender","age");

        String dataLabTrendShopping_cate =
                "https://openapi.naver.com/v1/datalab/shopping/categories";

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj = (JSONObject) jsonParser.parse(searchTrendShopping);
        ObjectMapper mapper = new ObjectMapper();

        // 쇼핑 분야별트랜드 결과(클릭량 추이)
        String responseBodyTotal = post(dataLabTrendShopping_cate,
                requestHeaders(), searchTrendShopping);

        ApiTrendResDto cateTrendResDto =
                mapper.readValue(
                        responseBodyTotal,
                        ApiTrendResDto.class);

        NaverApiTrendShoppingRes.trendRes trendRes =
                NaverApiTrendShoppingRes.trendRes.builder()
                        .title("cateClickTrend")
                        .cateTrendResDto(cateTrendResDto)
                        .build();

        // 검색 카테고리 리스트
        List<CategoryRequest> categoryList = cateTrendRequest.getCategory();
        categoryList.stream().forEach(cate -> {
            cateList.add(cate.getParam().get(0));
        });

        List cateTargetResultList = new ArrayList();

        // 기기별, 연령별, 성별 결과값 받아오기
        for (String cate : cateList) {
            for (int i = 0; i < urlList.size(); i++) {
                jsonObj.replace("category", cate);
                String responseBody=post(urlList.get(i), requestHeaders(),
                        jsonObj.toString());

                NaverApiTrendShoppingTargetRes.ApiTrendTargetResDto targetTrend =
                        mapper.readValue(responseBody, NaverApiTrendShoppingTargetRes.ApiTrendTargetResDto.class);

                NaverApiTrendShoppingTargetRes.NaverTargetTrend targetRes=
                        NaverApiTrendShoppingTargetRes.NaverTargetTrend.builder()
                                .target(resultList.get(i))
                                .category(cate)
                                .cateTargetTrend(targetTrend)
                                .build();

                cateTargetResultList.add(targetRes);
            }
        }

        NaverApiTrendShoppingRes.AllResponse allResponse =
                NaverApiTrendShoppingRes.AllResponse.builder()
                        .trendRes(trendRes)
                        .targetTrend(cateTargetResultList)
                        .build();

        String json = mapper.writeValueAsString(allResponse);
        log.info("json");
        System.out.println(json);

        return json;
    }


    private static String get(
            String apiURL,
            Map<String, String> requestHeaders
    ) {
        HttpURLConnection con = connect(apiURL);
        try {
            con.setRequestMethod("GET");
            for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 에러 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private static String post (String ApiUrl,
                                Map < String, String > requestHeaders,
                                String requestBody){
        HttpURLConnection connect = connect(ApiUrl);

        try {
            connect.setRequestMethod("POST");
            for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
                connect.setRequestProperty(header.getKey(), header.getValue());
            }

            connect.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(connect.getOutputStream())) {
                wr.write(requestBody.getBytes());
                wr.flush();
            }

            int responseCode = connect.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 응답
                return readBody(connect.getInputStream());
            } else {  // 에러 응답
                return readBody(connect.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            connect.disconnect(); // Connection을 재활용할 필요가 없는 프로세스일 경우
        }
    }

    private static HttpURLConnection connect (String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private static String readBody (InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body, StandardCharsets.UTF_8);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }

}

