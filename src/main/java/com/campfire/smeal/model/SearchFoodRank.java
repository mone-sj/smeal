package com.campfire.smeal.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class SearchFoodRank {
    // 음식명 검색 이력관리 테이블에서 count -> 순위 테이블

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String searchKeyword;

    // total count, mbtiType count
    private String type;
    private String count;
    private int rank;

    @CreationTimestamp
    private Timestamp createDate;

}
