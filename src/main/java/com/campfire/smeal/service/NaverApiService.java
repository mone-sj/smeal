package com.campfire.smeal.service;

import com.campfire.smeal.dto.api.NaverApiTrendShoppingRes;
import com.campfire.smeal.dto.api.NaverApiTrendShoppingRes.NaverClickTrendShoppingRes.ApiTrendResDto;
import com.campfire.smeal.dto.api.NaverApiTrendShoppingRes.NaverKeywordTrendShoppingRes.*;
import com.campfire.smeal.dto.api.NaverApiTrendShoppingRes.NaverKeywordTrendShoppingRes.Results;
import com.campfire.smeal.dto.api.NaverApiTrendShoppingTargetRes;
import com.campfire.smeal.dto.api.NaverApiTrendShoppingTargetRes.KeywordTargetTrendShoppingRes.*;
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

    // ????????? ?????? (????????? ?????? ????????? ??????)
    // https://developers.naver.com/docs/serviceapi/search/blog/blog.md#%EB%B8%94%EB%A1%9C%EA%B7%B8
    public NaverSearchRes.Root searchBlog(String searchWord)
            throws JsonProcessingException {
        String text = null;

        try {
            text = URLEncoder.encode(searchWord, "UTF-8");
            System.out.println("?????????: " + text);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("????????? ????????? ??????", e);
        }

        String searchBlog_apiURL =
                "https://openapi.naver.com/v1/search/blog?query=" + text + "&display=100";    // json ??????

        String responseBody = get(searchBlog_apiURL, requestHeaders());

        ObjectMapper mapper = new ObjectMapper();
        NaverSearchRes.Root res = mapper.readValue(
                responseBody, NaverSearchRes.Root.class);

        return res;

    }



    // ????????? ?????????????????? ???????????? ????????? ?????? - db ?????? ???
    public AllKeywordResponse keywordTrendDbSelect(
            KeywordTrendRequest keywordTrendRequest
    ) throws ParseException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String request =
                mapper.writeValueAsString(keywordTrendRequest);

        // ?????????
        // ????????? ????????? : keywordList
        // target Url : urlMap
        List<String> keywordList = new ArrayList<>();
        Map<String, String> urlMap = new HashMap<>();
        urlMap.put("gender", "https://openapi.naver.com/v1/datalab/shopping/category/keyword/gender");
        urlMap.put("age", "https://openapi.naver.com/v1/datalab/shopping/category/keyword/age");

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj = (JSONObject) jsonParser.parse(request);

        // ?????? ???????????? ?????????(????????? ??????)
        String dataLabTrendShopping_keyword
                = "https://openapi.naver.com/v1/datalab/shopping/category/keywords";
        String responseBodyTotal = post(dataLabTrendShopping_keyword,
                requestHeaders(), request);

        // naver responseBodyTotal ?????????
        ApiKeywordTrendResDto keywordTrendResDto =
                mapper.readValue(
                        responseBodyTotal,
                        ApiKeywordTrendResDto.class);

        // keyword??? ratio (clickTrend) ?????? ??????
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
        } //for??? ???

        Double divisionBytotalRatioSum =
                100 / result.values().stream().mapToDouble(Double::doubleValue).sum();

        List<String> ratioList = new ArrayList<>();
        // clickTrend ??????
        for (String key : result.keySet()) {
            result.put(key, result.get(key) * divisionBytotalRatioSum);
            keywordList.add(key);
            ratioList.add(String.format("%.2f",result.get(key)));
        }

        TotalClick totalClickValue = TotalClick.builder()
                .keywordList(keywordList)
                .ratioList(ratioList)
                .build();
        log.info("TotalClick");
        System.out.println(totalClickValue);

        // ?????????, ?????? (target) ?????? list ?????????
        ArrayList<TargetRes> ageResList = new ArrayList<>();
        ArrayList<TargetRes> genderResList = new ArrayList<>();

        // ???????????? ?????????, ?????? ????????? ????????????
        for (String keyword : keywordList) {
            for (String urlMapKey : urlMap.keySet()) {
                jsonObj.replace("keyword", keyword);

                // ??????
                String responseBody=post(urlMap.get(urlMapKey), requestHeaders(),
                        jsonObj.toString());

                // DTO??????
                ApiKeywordTrendTargetResDto keywordTargetTrend =
                        mapper.readValue(
                                responseBody,
                                ApiKeywordTrendTargetResDto.class);

                ArrayList<KeywordTargetRearrRes> keywordTargetRearrResList = new ArrayList<>();
                for (int j = 0; j < keywordTargetTrend.getResults().size(); j++) {
                    List<NaverApiTrendShoppingTargetRes.KeywordTargetTrendShoppingRes.ResultData>
                            targetResultResList = keywordTargetTrend.getResults().get(j).getData();

                    // group ??? ??????
                    List<String> groupList = new ArrayList<>();
                    for (NaverApiTrendShoppingTargetRes.KeywordTargetTrendShoppingRes.ResultData
                            resultData : targetResultResList) {
                        groupList.add(resultData.getGroup());
                    }
                    // ????????????
                    List<String> newGroupList = groupList.stream().distinct().collect(Collectors.toList());

                    // group??? ratio ????????? ??????
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

                    // targetResultMap= group:ratio  -> targetRearrRes ?????????
                    for (String key : targetResultMap.keySet()) {
                        targetResultMap.put(key, targetResultMap.get(key) * divisionByTargetRatioSum);
                        KeywordTargetRearrRes keywordTargetRearrRes =
                                KeywordTargetRearrRes.builder()
                                        .group(key)
                                        .ratio(String.format("%.2f",targetResultMap.get(key)))
                                        .build();
                        keywordTargetRearrResList.add(keywordTargetRearrRes);
                    }
                }

                if (urlMapKey.equals("age")) {
                    TargetRes ageRes =
                            TargetRes.builder()
                                    .keyword(keyword)
                                    .results(keywordTargetRearrResList)
                                    .build();
                    ageResList.add(ageRes);
                } else if (urlMapKey.equals("gender")) {
                    TargetRes genderRes =
                            TargetRes.builder()
                                    .keyword(keyword)
                                    .results(keywordTargetRearrResList)
                                    .build();
                    genderResList.add(genderRes);
                }
            }
        }

        AllKeywordResponse allKeywordResponse=
                AllKeywordResponse.builder()
                        .totalClick(totalClickValue)
                        .ages(targetValuePerGroup(ageResList))
                        .genders(targetValuePerGroup(genderResList))
                        .build();

        String responseJson = mapper.writeValueAsString(allKeywordResponse);
        System.out.println(responseJson);

        return allKeywordResponse;
    }

    // group??? ????????? ??????
    public TargetValueResponse targetValuePerGroup(ArrayList<TargetRes> results) {
        System.out.println(results);
        List<String> targetList = new ArrayList<>();
        List<String> resultValuesList = new ArrayList<>();
        Map<String, List<String>> valueMap = new HashMap<>();

        for (TargetRes result : results){
            targetList.add(result.getKeyword());
            for (KeywordTargetRearrRes value : result.getResults()) {
                if (valueMap.containsKey(value.getGroup())) {
                    resultValuesList = valueMap.get(value.getGroup());
                    resultValuesList.add(value.getRatio());
                    valueMap.replace(value.getGroup(), resultValuesList);
                    resultValuesList = new ArrayList<>();
                } else {
                    resultValuesList.add(value.getRatio());
                    valueMap.put(value.getGroup(), resultValuesList);
                    resultValuesList = new ArrayList<>();
                }
            }
        }

        TargetValueResponse targetValueResponse = TargetValueResponse.builder()
                .keyword(targetList)
                .results(valueMap)
                .build();
        return targetValueResponse;
    }



    /*
    ????????? ???????????? ??????

    */

    /*????????? ??????-?????????*/
    public NaverSearchRes.img.Root searchImage(String searchWord)
            throws JsonProcessingException {

        String text = null;
        try {
            text = URLEncoder.encode(searchWord, "UTF-8");
            System.out.println("?????????: " + text);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("????????? ????????? ??????", e);
        }

        String searchImage_apiURL =
                "https://openapi.naver.com/v1/search/image?query=" + text;    // json ??????

        String responseBody = get(searchImage_apiURL, requestHeaders());
        System.out.println(responseBody);

        ObjectMapper mapper = new ObjectMapper();
        NaverSearchRes.img.Root res = mapper.readValue(
                responseBody, NaverSearchRes.img.Root.class);
        return res;

    }

    public String searchImageOrigin(String searchWord) {

        String text = null;
        try {
            text = URLEncoder.encode(searchWord, "UTF-8");
            System.out.println("?????????: " + text);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("????????? ????????? ??????", e);
        }

        String searchImage_apiURL =
                "https://openapi.naver.com/v1/search/image?query=" + text;    // json ??????

        String responseBody = get(searchImage_apiURL, requestHeaders());
        System.out.println(responseBody);
        return responseBody;
    }

    // ????????? ?????????????????? ???????????? ????????? ?????? - requestBody??? ??? ???????????? -???????????? ??????
    public AllKeywordResponse keywordTrendShopping(String req
    ) throws ParseException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        // ???????????? req??? ?????? ?????????
        KeywordTrendRequest keywordTrendRequest =
                keywordTrendRequestDefaultDto(req);

        String request =
                mapper.writeValueAsString(keywordTrendRequest);

        // ?????????
        // ????????? ????????? : keywordList
        // target Url : urlMap
        List<String> keywordList = new ArrayList<>();
        Map<String, String> urlMap = new HashMap<>();
        urlMap.put("gender", "https://openapi.naver.com/v1/datalab/shopping/category/keyword/gender");
        urlMap.put("age", "https://openapi.naver.com/v1/datalab/shopping/category/keyword/age");

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj = (JSONObject) jsonParser.parse(request);

        // ?????? ???????????? ?????????(????????? ??????)
        String dataLabTrendShopping_keyword
                = "https://openapi.naver.com/v1/datalab/shopping/category/keywords";
        String responseBodyTotal = post(dataLabTrendShopping_keyword,
                requestHeaders(), request);

        // naver responseBodyTotal ?????????
        ApiKeywordTrendResDto keywordTrendResDto =
                mapper.readValue(
                        responseBodyTotal,
                        ApiKeywordTrendResDto.class);

        // keyword??? ratio (clickTrend) ?????? ??????
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
        } //for??? ???

        Double divisionBytotalRatioSum =
                100 / result.values().stream().mapToDouble(Double::doubleValue).sum();

        List<String> ratioList = new ArrayList<>();
        // clickTrend ??????
        for (String key : result.keySet()) {
            result.put(key, result.get(key) * divisionBytotalRatioSum);
            keywordList.add(key);
            ratioList.add(String.format("%.2f",result.get(key)));
        }

        TotalClick totalClickValue = TotalClick.builder()
                .keywordList(keywordList)
                .ratioList(ratioList)
                .build();
        log.info("TotalClick");
        System.out.println(totalClickValue);

        // ?????????, ?????? (target) ?????? list ?????????
        ArrayList<TargetRes> ageResList = new ArrayList<>();
        ArrayList<TargetRes> genderResList = new ArrayList<>();

        // ???????????? ?????????, ?????? ????????? ????????????
        for (String keyword : keywordList) {
            for (String urlMapKey : urlMap.keySet()) {
                jsonObj.replace("keyword", keyword);

                // ??????
                String responseBody=post(urlMap.get(urlMapKey), requestHeaders(),
                        jsonObj.toString());

                // DTO??????
                ApiKeywordTrendTargetResDto keywordTargetTrend =
                        mapper.readValue(
                                responseBody,
                                ApiKeywordTrendTargetResDto.class);

                ArrayList<KeywordTargetRearrRes> keywordTargetRearrResList = new ArrayList<>();
                for (int j = 0; j < keywordTargetTrend.getResults().size(); j++) {
                    List<NaverApiTrendShoppingTargetRes.KeywordTargetTrendShoppingRes.ResultData>
                            targetResultResList = keywordTargetTrend.getResults().get(j).getData();

                    // group ??? ??????
                    List<String> groupList = new ArrayList<>();
                    for (NaverApiTrendShoppingTargetRes.KeywordTargetTrendShoppingRes.ResultData
                            resultData : targetResultResList) {
                        groupList.add(resultData.getGroup());
                    }
                    // ????????????
                    List<String> newGroupList = groupList.stream().distinct().collect(Collectors.toList());

                    // group??? ratio ????????? ??????
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

                    // targetResultMap= group:ratio  -> targetRearrRes ?????????
                    for (String key : targetResultMap.keySet()) {
                        targetResultMap.put(key, targetResultMap.get(key) * divisionByTargetRatioSum);
                        KeywordTargetRearrRes keywordTargetRearrRes =
                                KeywordTargetRearrRes.builder()
                                        .group(key)
                                        .ratio(String.format("%.2f",targetResultMap.get(key)))
                                        .build();
                        keywordTargetRearrResList.add(keywordTargetRearrRes);
                    }
                }

                if (urlMapKey.equals("age")) {
                    TargetRes ageRes =
                            TargetRes.builder()
                                    .keyword(keyword)
                                    .results(keywordTargetRearrResList)
                                    .build();
                    ageResList.add(ageRes);
                } else if (urlMapKey.equals("gender")) {
                    TargetRes genderRes =
                            TargetRes.builder()
                                    .keyword(keyword)
                                    .results(keywordTargetRearrResList)
                                    .build();
                    genderResList.add(genderRes);
                }
            }
        }

        AllKeywordResponse allKeywordResponse=
                AllKeywordResponse.builder()
                        .totalClick(totalClickValue)
                        .ages(targetValuePerGroup(ageResList))
                        .genders(targetValuePerGroup(genderResList))
                        .build();

        String responseJson = mapper.writeValueAsString(allKeywordResponse);
        System.out.println(responseJson);

        return allKeywordResponse;
    }

    // ????????? ?????? ????????? ????????? API
    // https://developers.naver.com/docs/serviceapi/datalab/search/search.md#%ED%86%B5%ED%95%A9-%EA%B2%80%EC%83%89%EC%96%B4-%ED%8A%B8%EB%A0%8C%EB%93%9C
    public String datalabSearchTrend(String searchTrendRequest) {
        String apiUrl = "https://openapi.naver.com/v1/datalab/search";

        String responseBody = post(apiUrl, requestHeaders(), searchTrendRequest);
        System.out.println(responseBody);
        return responseBody;
    }

    // ????????? ?????????????????? - ????????? ????????? ??????
    public String cateTrendShopping(String searchTrendShopping,
                                    CateTrendRequest cateTrendRequest
    ) throws ParseException, JsonProcessingException {
        // searchTrendShopping, cateTrendRequest ?????? ???. ???????????? ??????

        // ?????????
        // ???????????? ????????? : paramList
        // url ?????????: urlList
        // target ?????????(??????,???,?????????) : resultList
        List<String> cateList = new ArrayList<>();
        List<String> urlList = Arrays.asList(
                //"https://openapi.naver.com/v1/datalab/shopping/category/device", // ?????????
                "https://openapi.naver.com/v1/datalab/shopping/category/gender", // ??????
                "https://openapi.naver.com/v1/datalab/shopping/category/age"); // ?????????
        List<String> resultList = Arrays.asList("gender","age");

        String dataLabTrendShopping_cate =
                "https://openapi.naver.com/v1/datalab/shopping/categories";

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj = (JSONObject) jsonParser.parse(searchTrendShopping);
        ObjectMapper mapper = new ObjectMapper();

        // ?????? ?????????????????? ??????(????????? ??????)
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

        // ?????? ???????????? ?????????
        List<CategoryRequest> categoryList = cateTrendRequest.getCategory();
        categoryList.stream().forEach(cate -> {
            cateList.add(cate.getParam().get(0));
        });

        List cateTargetResultList = new ArrayList();

        // ?????????, ?????????, ?????? ????????? ????????????
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
            if (responseCode == HttpURLConnection.HTTP_OK) { // ?????? ??????
                return readBody(con.getInputStream());
            } else { // ?????? ??????
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API ????????? ?????? ??????", e);
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
            if (responseCode == HttpURLConnection.HTTP_OK) { // ?????? ??????
                return readBody(connect.getInputStream());
            } else {  // ?????? ??????
                return readBody(connect.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API ????????? ?????? ??????", e);
        } finally {
            connect.disconnect(); // Connection??? ???????????? ????????? ?????? ??????????????? ??????
        }
    }

    private static HttpURLConnection connect (String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL??? ?????????????????????. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("????????? ??????????????????. : " + apiUrl, e);
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
            throw new RuntimeException("API ????????? ????????? ??????????????????.", e);
        }
    }

}

