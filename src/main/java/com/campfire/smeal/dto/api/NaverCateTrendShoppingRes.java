package com.campfire.smeal.dto.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.openid.connect.sdk.claims.Gender;
import lombok.*;

import java.util.List;

// 네이버 분야별 쇼핑트렌드_복합적으로 결과값 모두 응답 (통합) - 삭제해도 될듯?
//
//@Getter
//@Setter
//@Builder
//public class NaverCateTrendShoppingRes {
//
//    @Data
//    @NoArgsConstructor
//    @AllArgsConstructor
//    @Builder
//    public static class DeviceRatio {
//        private String pc;
//        private String mo;
//    }
//
//    @Data
//    @NoArgsConstructor
//    @AllArgsConstructor
//    @Builder
//    public static class GenderRatio {
//        private String f;
//        private String m;
//    }
//
//    @Data
//    @NoArgsConstructor
//    @AllArgsConstructor
//    @Builder
//    public static class AgesRatio {
//        private String age10;
//        private String age20;
//        private String age30;
//        private String age40;
//        private String age50;
//        private String age60;
//    }
//
//    @Data
//    @NoArgsConstructor
//    @AllArgsConstructor
//    @Builder
//    public static class ResultData {
//        private String period;
//        private String trendRatio;
//        private DeviceRatio deviceRatio;
//        private GenderRatio genderRatio;
//        private AgesRatio ageRatio;
//    }
//
//    @Data
//    @NoArgsConstructor
//    @AllArgsConstructor
//    @Builder
//    public static class Results{
//        private String title;
//        private String category;
//        private ResultData resultData;
//    }
//
//    @Data
//    @NoArgsConstructor
//    @AllArgsConstructor
//    @Builder
//    public static class CateTrendResponse {
//        private String startDate;
//        private String endDate;
//        private String timeUnit;
//
//        private List<Results> results;
//    }
//
//    public static CateTrendResponse toCateTrendResDto(String searchTrendShoppingResult)
//            throws JsonProcessingException {
//
//        ObjectMapper mapper = new ObjectMapper();
//        CateTrendResponse cateTrendResponse =
//                mapper.readValue(searchTrendShoppingResult, CateTrendResponse.class);
//
//        System.out.println("cateTrendResponse: ");
//        System.out.println(cateTrendResponse);
//
//        return null;
//
////        CateTrendResponse cateTrendRequestToBuild = CateTrendResponse.builder()
////                        .startDate(cateTrendRequest.startDate)
////                        .endDate(cateTrendRequest.endDate)
////                        .timeUnit(cateTrendRequest.timeUnit)
////                        .device(cateTrendRequest.getDevice())
////                        .gender(cateTrendRequest.getGender())
////                        .ages(cateTrendRequest.getAges())
////                        .category(cateTrendRequest.getCategory())
////                        .build();
////        return cateTrendRequestToBuild;
//    }
//}
