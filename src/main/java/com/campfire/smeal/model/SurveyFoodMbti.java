package com.campfire.smeal.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class SurveyFoodMbti {
    // MBTI 설문지

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String qNo;

    @Column(columnDefinition = "TEXT")
    private String question;





    // enum 타입으로 작성시
//    @Enumerated(EnumType.STRING)
//    private FoodMbtiCate FoodMbtiName;

    //private String FoodMbtiName;

    // MBTI category 설명
//    @Column(columnDefinition = "TEXT")
//    private String description;
//
//    @Column(length = 1000)
//    private String recommendFood;
//
//    @Column(length = 1000)
//    private String recommendIngredient;
}
