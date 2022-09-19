package com.campfire.smeal.dto.api;

import com.campfire.smeal.dto.api.NaverApiTrendShoppingRes.NaverClickTrendShoppingRes.ApiTrendResDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.campfire.smeal.dto.api.NaverApiTrendShoppingTargetRes.KeywordTargetTrendShoppingRes;
import static com.campfire.smeal.dto.api.NaverApiTrendShoppingTargetRes.NaverTargetTrend;

// api 응답값 - 쇼핑인사이트 분야별/키워드별 클릭량 트렌드 결과
public class NaverApiTrendShoppingRes {

    // 분야별 트렌드 결과
    public static class NaverClickTrendShoppingRes {
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        private static class ResultData {
            private String period;
            private String ratio;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        private static class Results{
            private String title;
            private List<String> category;
            private List<ResultData> data;
        }


        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class ApiTrendResDto {
            private String startDate;
            private String endDate;
            private String timeUnit;
            private List<Results> results;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class trendRes {
        private String title;
        private ApiTrendResDto cateTrendResDto;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AllResponse {
        private trendRes trendRes;
        private List<NaverTargetTrend> targetTrend;
    }

    public static ApiTrendResDto toNaverApiTrendResDto(
            String response)
            throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ApiTrendResDto naverResponse =
                mapper.readValue(response, ApiTrendResDto.class);

        ApiTrendResDto naverResponseToBuild = naverResponse.builder()
                .startDate(naverResponse.startDate)
                .endDate(naverResponse.endDate)
                .timeUnit(naverResponse.timeUnit)
                .results(naverResponse.getResults())
                .build();

        return naverResponseToBuild;
    }


    // 키워드별 트렌드 응답
    public static class NaverKeywordTrendShoppingRes {
        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class ResultData {
            private String period;
            private String ratio;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class Results{
            private String title;
            private List<String> keyword;
            private List<ResultData> data;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class ApiKeywordTrendResDto {
            private String startDate;
            private String endDate;
            private String timeUnit;
            private List<Results> results;
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class TotalClick {
            private List<String> keywordList;
            private List<String> ratioList;
        }


        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class AllKeywordResponse{
            public TotalClick totalClick;
            public KeywordTargetTrendShoppingRes.TargetValueResponse ages;
            public KeywordTargetTrendShoppingRes.TargetValueResponse genders;
        }



        public static ApiTrendResDto toNaverApiTargetTrendResDto(
                String response)
                throws JsonProcessingException {
            ObjectMapper mapper = new ObjectMapper();
            ApiTrendResDto naverResponse =
                    mapper.readValue(response, ApiTrendResDto.class);

            ApiTrendResDto naverResponseToBuild = naverResponse.builder()
                    .startDate(naverResponse.startDate)
                    .endDate(naverResponse.endDate)
                    .timeUnit(naverResponse.timeUnit)
                    .results(naverResponse.getResults())
                    .build();

            return naverResponseToBuild;
        }


    }

}
