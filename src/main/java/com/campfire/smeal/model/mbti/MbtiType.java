package com.campfire.smeal.model.mbti;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MbtiType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String typeCode;
    private String typeName;
    private String pictureUrl;

    // type 설명
    @Column(columnDefinition = "TEXT")
    private String description;

    private String recommendFood;
}
