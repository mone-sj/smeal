package com.campfire.smeal.model.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class SubGroupInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int subId;

    private String foodName;

    //네이버의 category
    private String naverCatCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="mainId")
    private MainGroupInfo mainGroupInfo;
}
