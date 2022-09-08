package com.campfire.smeal.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

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

}
