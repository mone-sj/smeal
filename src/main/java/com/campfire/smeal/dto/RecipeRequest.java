package com.campfire.smeal.dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RecipeRequest {
    private String startIdx;
    private String endIdx;
    private String rcp_nm;
    private String rcp_parts_dtls;

}
