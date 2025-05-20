package com.example.bullsandcows.service;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
// index.mustache에서 출력할 GameResult, GameHisytory 2개의 클래스와 gameEnd 불린 값
// 게임 진행 결과 - GameResult 리턴 값
// 게인 진행 히스토리 - GameHistory 클래스에서 getGameHistory를 통해 index에서 출력
// 게임 끝남 여부 - isGameEnd를 통해 gameEnd를 get해서 index에서 출력
// play 메소드 - 스트라이크, 볼 카운트가 끝나면 History 클래스 생성해서 history 리스트에 add
//            - 리턴 GameResult 클래스 생성(스트라이크, 볼, 끝남 여부)
public class NumberBaseball {
    @Getter
    private List<Integer> setBase; // 3개의 랜덤한 숫자
    private int tryCount; // 한 게임의 총 진행 횟수
    // index에 반복, 조건을 넣을 gamHistory gameEnd 롬복을 통해 get
    @Getter
    private final List<GameHistory> gamHistory = new ArrayList<>(); // 진행 결과
    @Getter
    private boolean gameEnd = false; // 게임 종료 여부

    // 자동 초기화
    public NumberBaseball() {
        clearGame();
    }

    // 게임 리셋
    public void clearGame() {
        setBase = generateBaseNumber();
        tryCount = 0;
        gameEnd = false;
        gamHistory.clear();
    }

    // 지금까지 진행한 게임 횟수 반환
    public int getTryCount() {
        return this.tryCount;
    }

    // 3개의 중복되지 않는 랜덤한 숫자를 배열로 만듦
    private List<Integer> generateBaseNumber() {
        List<Integer> baseNumber = new ArrayList<>();
        Random rand = new Random();
        while (baseNumber.size() < 3) {
            int n = rand.nextInt(9) + 1;
            if (!baseNumber.contains(n)) {
                baseNumber.add(n);
            }
        }
        return baseNumber;
    }

    // 한 게임에서 1회 진행하는 메소드
    public GameResult play(List<Integer> input) {
        if (gameEnd) return new GameResult(0, 0);

        int strikes = 0;
        int balls = 0;

        for (int i = 0; i < 3; i++) {
            int userDigit = input.get(i);
            if (userDigit == setBase.get(i)) {
                strikes++;
            } else if (setBase.contains(userDigit)) {
                balls++;
            }
        }
        tryCount++;
        // gameHistory에 객체를 넣어줌
        gamHistory.add(new GameHistory(input, strikes, balls));

        if (strikes == 3) {
            gameEnd = true; // 게임 종료
            // 여기에서 tryCount를 DB에 넣는 코드가 필요할 것 같음
            // --> Service layer는 단순한 게임 로직 일뿐 DB 저장을 위해서는 Controller에서 진행
        }
        return new GameResult(strikes, balls);
    }



    // GameResult, GameHistory 클래스 - 롬복을 통해 get메소드
    // 1회 진행의 스트라이크, 볼, 끝남 여부를 갖는 GameResult 클래스
    @Getter
    public static class GameResult {
        private final int strikes;
        private final int balls;

        public GameResult(int strikes, int balls) {
            this.strikes = strikes;
            this.balls = balls;
        }
    }

    // 1회 진행의 인풋 값과 스트라이크, 볼을 갖는 History 클래스
    @Getter
    public static class GameHistory {
        private final List<Integer> inputs;
        private final int strikeCount;
        private final int ballCount;

        public GameHistory(List<Integer> inputs, int strikeCount, int ballCount) {
            this.inputs = inputs;
            this.strikeCount = strikeCount;
            this.ballCount = ballCount;
        }
    }

}
