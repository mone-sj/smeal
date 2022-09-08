package com.campfire.smeal.dto.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.text.SimpleDateFormat;
import java.util.*;

// 네이버 키워드별 트렌드값 요청

@Slf4j
@Getter
@Setter
@Builder
public class NaverKeywordTrendShoppingReq {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Keyword{
        private String name;
        private List<String> param;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class KeywordTrendRequest {
        private String startDate;
        private String endDate;
        private String timeUnit;
        private String category;

        private List<Keyword> keyword;
        private String device;
        private String gender;
        private List<String> ages;
    }

    // 추후 필요없어질 수 있음
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class KeywordTrendRequestParam{
        private String param;
    }

    // 추후 필요없어질 수 있음
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class KeywordTrendRequestKeyword {
        private List<KeywordTrendRequestParam> keyword;
    }


    public static KeywordTrendRequest keywordTrendRequestDefaultDto(
            String request
    ) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        KeywordTrendRequestKeyword keywordTrendRequestKeyword =
                mapper.readValue(request, KeywordTrendRequestKeyword.class);

        List<Keyword> keywordList = new ArrayList<>();
        for (int i = 0; i < keywordTrendRequestKeyword.getKeyword().size(); i++) {
            List<String> paramList = new ArrayList<>();
            paramList.add(keywordTrendRequestKeyword.getKeyword().get(i).getParam());
            Keyword keywordValue = Keyword.builder()
                    .name("식품/" + keywordTrendRequestKeyword.getKeyword().get(i).getParam())
                    .param(paramList)
                    .build();
            keywordList.add(keywordValue);
        }

        HashMap<String, String> datePeriod = datePeriod();

        KeywordTrendRequest keywordTrendRequestToBuild = KeywordTrendRequest.builder()
                .startDate(datePeriod.get("startDate"))
                .endDate(datePeriod.get("endDate"))
                .timeUnit("month")
                // 카테고리 식품으로 고정
                .category("50000006")
                .keyword(keywordList)
                .device("")
                .gender("")
                .ages(new ArrayList<>())
                .build();

        return keywordTrendRequestToBuild;
    }

    // params를 리스트로 받을때,
    public static KeywordTrendRequest keywordTrendRequestDefaultDto(
            List<String> params
    ){
        ObjectMapper mapper = new ObjectMapper();

        List<Keyword> keywordList = new ArrayList<>();
        for (int i = 0; i < params.size(); i++) {
            List<String> paramList = new ArrayList<>();
            paramList.add(params.get(i));
            Keyword keywordValue = Keyword.builder()
                    .name("식품/" + params.get(i))
                    .param(paramList)
                    .build();
            keywordList.add(keywordValue);
        }

        HashMap<String, String> datePeriod = datePeriod();

        KeywordTrendRequest keywordTrendRequestToBuild = KeywordTrendRequest.builder()
                .startDate(datePeriod.get("startDate"))
                .endDate(datePeriod.get("endDate"))
                .timeUnit("month")
                // 카테고리 식품으로 고정
                .category("50000006")
                .keyword(keywordList)
                .device("")
                .gender("")
                .ages(new ArrayList<>())
                .build();

        return keywordTrendRequestToBuild;
    }


    public static KeywordTrendRequest toKeywordTrendRequestDto(
            String request
            ) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        KeywordTrendRequest cateTrendRequest =
                mapper.readValue(request, KeywordTrendRequest.class);

        KeywordTrendRequest keywordTrendRequestToBuild = KeywordTrendRequest.builder()
                .startDate(cateTrendRequest.startDate)
                .endDate(cateTrendRequest.endDate)
                .timeUnit(cateTrendRequest.timeUnit)
                .category(cateTrendRequest.getCategory())
                .keyword(cateTrendRequest.getKeyword())
                .device(cateTrendRequest.getDevice())
                .gender(cateTrendRequest.getGender())
                .ages(cateTrendRequest.getAges())
                .build();

        return keywordTrendRequestToBuild;
    }

    public static HashMap<String, String> datePeriod() {
        HashMap<String, String> period = new HashMap<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, -1);

        String endDate = formatter.format(cal.getTime());
        period.put("endDate", endDate);

        cal.add(Calendar.MONTH, -2);
        String startDate = formatter.format(cal.getTime());
        period.put("startDate", startDate);

        return period;
    }

}
