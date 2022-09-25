package com.campfire.smeal.dto.mbti;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NonMemberInfoDto {
    private String age;
    private String gender;
    private String mbtiType;
}
