package com.campfire.smeal.dto.mbti;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MbtiResponseDto {
    private String qNo;
    private String result;
    private String resultTypeCode;

    private String age;
    private String gender;
    private String memberStatus;
}
