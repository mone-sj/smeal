package com.campfire.smeal.model;

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

//    @Enumerated(EnumType.STRING)
//    private MbtiTypeArr TypeName;

    private String typeCode;
    private String typeName;

    // type 설명
    @Column(columnDefinition = "TEXT")
    private String description;

    private String recommendFood;
    private String recommendIngredient;
}
