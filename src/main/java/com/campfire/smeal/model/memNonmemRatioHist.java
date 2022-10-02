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

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class memNonmemRatioHist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int members;
    private int nonMembers;
    private int total;

    @CreationTimestamp
    private Timestamp createDate;
}
