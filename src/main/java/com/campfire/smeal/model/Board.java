package com.campfire.smeal.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String title;


    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="userId")
    private User user;


//    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
//    @JsonIgnoreProperties({"board"})
//    @OrderBy("id asc")
//    private List<Reply> replys;

    @ColumnDefault("0")
    private int views; //조회수

    @CreationTimestamp
    private Timestamp createDate;

    @LastModifiedDate
    private Timestamp updateDate;
}

