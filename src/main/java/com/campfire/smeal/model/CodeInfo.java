package com.campfire.smeal.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class CodeInfo {
    // 중분류-소분류의 전체 리스트

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 중분류-소분류
    private String group;

    private String foodName;

    //네이버의 category
    private String naverCatId;

    // 검색률
    private Long searchCount;
}
