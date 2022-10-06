package com.campfire.smeal.model.mbti;


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

}
