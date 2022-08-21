package com.campfire.smeal.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NaverSearchTrendShopping {
    private String startDate;
    private String endDate;
    private String timeUnit;
    private List<String> category;
    private String category_name;
    private List<String> category_param;
    private String device;
    private String gender;
    private List<String> ages;

}
