package com.campfire.smeal.model.mbti;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class SurveyResultFoodMbti {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName="qNo")
    private SurveyFoodMbti surveyFoodMbti;

    private String result;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName="typeCode")
    private MbtiType mbtiResult;

    private String age;
    private String gender;
    private String memberStatus;


}
