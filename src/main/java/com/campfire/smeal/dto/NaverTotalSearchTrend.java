package com.campfire.smeal.dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Getter
@Setter
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class NaverTotalSearchTrend {

    @NonNull
    private String startDate;
    @NonNull
    private String endDate;
    @NonNull
    private String timeUnit;
    @NonNull
    private List<String> keywordGroups;

    private String keywordGroups_groupName;
    private List<String> keywordGroups_keywords;

    private String device;
    private String gender;
    private List<String> ages;

//    @Getter
//    @Setter
//    @Slf4j
//    @AllArgsConstructor
//    @NoArgsConstructor
//    @Builder
//    @ToString
//    public static class Request{
//        @NonNull
//        private String startDate;
//        @NonNull
//        private String endDate;
//        @NonNull
//        private String timeUnit;
//        @NonNull
//        private List<String> keywordGroups;
//
//        private String keywordGroups_groupName;
//        private List<String> keywordGroups_keywords;
//
//        private String device;
//        private String gender;
//        private List<String> ages;
//    }


}
