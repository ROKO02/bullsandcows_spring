package com.example.bullsandcows.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Entity @ToString
@Getter @Setter
public class Ranking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String userID;

    @Column
    private int tryCount;

//    @Column
//    private LocalDateTime playTime;
}
