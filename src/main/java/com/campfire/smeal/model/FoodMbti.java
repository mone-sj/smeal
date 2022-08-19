package com.campfire.smeal.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Entity
public class FoodMbti { // MBTI type을 합쳤을때

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // enum 타입으로 작성시
//    @Enumerated(EnumType.STRING)
//    private FoodMbtiCate FoodMbtiName;

    private String FoodMbtiName;

    @Column(columnDefinition = "TEXT") // 설명
    private String description;

    @Column(length = 1000)
    private String recommendFood;

    @Column(length = 1000)
    private String recommendIngredient;
}