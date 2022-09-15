package com.campfire.smeal.model.search;

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
public class MainGroupInfo {
    // 중분류(MainGroup) 정보

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mainId;

    private String foodName;

    //네이버의 category
    private String naverCatCode;

}
