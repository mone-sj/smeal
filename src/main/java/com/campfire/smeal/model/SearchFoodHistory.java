package com.campfire.smeal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class SearchFoodHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String searchFoodName;

    @CreationTimestamp
    private Timestamp searchDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"user"})
    @JoinColumns({
            @JoinColumn(name = "userid", referencedColumnName = "id"),
            @JoinColumn(name = "userFoodMbti", referencedColumnName = "foodMbti")
    })
    private User user;

}
