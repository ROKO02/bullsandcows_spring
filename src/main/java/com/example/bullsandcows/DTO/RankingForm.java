package com.example.bullsandcows.DTO;

import com.example.bullsandcows.Entity.Ranking;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;


@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@ToString
public class RankingForm {
    private String userID; // schema의 userID
    private int tryCount; // schema의 tryCount


//    public RankingForm(int number1,int number2, int number3) {
//        this.number1= number1;
//        this.number2= number2;
//        this.number3= number3;
//    }

//    @Override
//    public String toString() {
//        String log = "";
//        log += "1번쨰 수: "+number1 + " " + "2번쨰 수 :"
//                + number2 + " "+ "3번쨰 수: " + number3 + "\n";
//        return log;
//    }

    // dto - > toEntity
    public Ranking toEntity() {
        Ranking ranking = new Ranking();
        ranking.setUserID(userID); // userID 또한 Entity로 같이 변환
        ranking.setTryCount(this.tryCount);
        return ranking;
    }
}
