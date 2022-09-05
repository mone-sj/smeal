package com.campfire.smeal.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Entity
public class FoodCate {
    // 검색할 음식의 카테고리

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String foodGroup;

    @OneToOne
    @JoinColumn(name="foodName")
    private String foodName;

}
