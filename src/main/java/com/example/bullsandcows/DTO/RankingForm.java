package com.example.bullsandcows.DTO;

import com.example.bullsandcows.Entity.Ranking;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;


@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@ToString
public class RankingForm {
    private int rank; // DB에는 rank가 없지만 뷰에 전달하기 위해 주가
    private String userID; // schema의 userID
    private int tryCount; // schema의 tryCount

    // dto - > toEntity
    public Ranking toEntity() {
        Ranking ranking = new Ranking();
        ranking.setUserID(userID); // userID 또한 Entity로 같이 변환
        ranking.setTryCount(this.tryCount);
        return ranking;
    }
}
