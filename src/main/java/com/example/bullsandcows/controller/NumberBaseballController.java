package com.example.bullsandcows.controller;

import com.example.bullsandcows.DTO.RankingForm;
import com.example.bullsandcows.Entity.Ranking;
import com.example.bullsandcows.Repository.RankingRepository;
import com.example.bullsandcows.service.NumberBaseball;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
public class NumberBaseballController {
    // 의존성 주입
    @Autowired
    RankingRepository rankingRepository;

    // NumberBaseball.java 가져오기
    private final NumberBaseball numberBaseball;
    public NumberBaseballController(NumberBaseball numberBaseball) {
        this.numberBaseball = numberBaseball;
    }

    // login 페이지
    @GetMapping("/login")
    public String login(Model model){
//        // id, pw 로그인 -> redriect
//
//        // 1. id, pw 로그인 -> DB에 id, pw 맞는지 검사 - 패스
//        // 2. x -> 회원가입 -> DB 저장 - 패스
//        // 3. o -> redirect bullsandcows
        return "login";
    }

    @PostMapping("/loginuser")
    public String user(@RequestParam(name = "userId") String userId, //index의 각 input태그로부터 파라미터 가져옴
                       @RequestParam(name = "userPw") String userPw,
                       final HttpServletRequest request){

        HttpSession user = request.getSession();
        user.setAttribute("id", userId);
        user.setAttribute("pw", userPw);

        return "redirect:/bullsandcows";
    }

    @GetMapping("/logoutuser")
    public String logout(HttpServletRequest request){

        HttpSession session = request.getSession();
        session.invalidate();

        return "redirect:/login";
    }

    // login 페이지->초기 페이지 경로
    @GetMapping("/bullsandcows")
    public String index(Model model,final HttpServletRequest request) {
        // 시작 페이지에서 기존에 넣어줬던 더미 데이터 출력
        List<Ranking> rankingEnitylist = rankingRepository.findAllByOrderByTryCountAsc();
        HttpSession session = request.getSession();
        // user ID/PW 확인
//        model.addAttribute("id", id);
//        model.addAttribute("pw", pw);
        String id = String.valueOf(session.getAttribute("id"));
        String pw = String.valueOf(session.getAttribute("pw"));

        model.addAttribute("rankinglist",rankingEnitylist);
        model.addAttribute("id", id);
        model.addAttribute("pw", pw);
        // user ID/PW 확인
//        model.addAttribute("id", id);
//        model.addAttribute("pw", pw);

        // 새 게임 시작 -> 기본 값 설정
        model.addAttribute("setBase", numberBaseball.getSetBase());
        model.addAttribute("history", numberBaseball.getGamHistory());
        model.addAttribute("finished", false); // 처음엔 게임 안 끝났으니까 false
        return "index";
    }

    // 제출 버튼 -> 게임 진행
    @PostMapping("/play")
    public String guess(@RequestParam(name = "number1") int number1, //index의 각 input태그로부터 파라미터 가져옴
                        @RequestParam(name = "number2") int number2,
                        @RequestParam(name = "number3") int number3,
                        final HttpServletRequest request,
                        Model model) {
        List<Integer> input = Arrays.asList(number1, number2, number3); // input 태그 값을 배열로

        // NumberBaseball play 메소드(스트라이크/볼)을 GameResult 클래스 result에 넣어줌
        NumberBaseball.GameResult result = numberBaseball.play(input); // GameResult rest = new GameResult(strikes, balls)
        HttpSession session = request.getSession();
        String id = String.valueOf(session.getAttribute("id"));
        String pw = String.valueOf(session.getAttribute("pw"));

        model.addAttribute("id", id);
        model.addAttribute("pw", pw);
        // 게임이 끝났는지 체그하는 flag
       boolean isFinished = numberBaseball.isGameEnd();
//        // dto가 저장되는지 확인위한 로그
//        System.out.println(rankingForm.toString());
//
//        // dto -> entity
//        Ranking ranking = rankingForm.toEntity();
//        System.out.println(ranking.toString());
//
//        // entity-> repository
//        Ranking saved = rankingRepository.save(ranking);
//        System.out.println(saved.toString());

        // dto -> enity-> repository
        if(isFinished) { // 게임이 끝날 경우(정답을 맞춘경우) DB에 저장된다.

            Ranking ranking = new Ranking();
            ranking.setUserID(id); // 위에 세션으로 가져온 아이디를 DB에 저정한다.
            ranking.setTryCount(numberBaseball.getTryCount());
            rankingRepository.save(ranking);

            // 마찬가지로 게임이 끝난 경우 DB에 저장된 데이터를 가져와 보여 줄 수 있게 한다

            //  Repo에서 entity리스트로 데이터 가져오기
            List<Ranking> rankingEntityList = rankingRepository.findAllByOrderByTryCountAsc();

            model.addAttribute("rankinglist", rankingEntityList);
            // 게임을 맞춘 경우
            numberBaseball.clearGame(); // 저장 후 게임 초기화
        }

        // 마찬가지로 게임이 끝난 경우 DB에 저장된 데이터를 가져와 보여 줄 수 있게 한다
        List<Ranking> rankingEntityList = rankingRepository.findAll();

        model.addAttribute("rankingList",rankingEntityList);

        // model에 등록하기
        model.addAttribute("setBase", numberBaseball.getSetBase());
        model.addAttribute("result", result); // 게임 결과
        model.addAttribute("history", numberBaseball.getGamHistory()); // 게임 진행
        model.addAttribute("finished", isFinished); // 게임 끝 메세지 출력 -> flag로 상태 전달
        return "index";
    }

    // 리셋 -> 게임 초기화
    @GetMapping("/restart")
    public String restart() {
        numberBaseball.clearGame();
        return "redirect:/bullsandcows";
    }

    // DB내용 페이지를 따로출력 할 경우
//    @GetMapping("/allresult")
//    public String index(Model model) {
//        //  Repo에서 entity리스트로 데이터 가져오기
//        List<Ranking> rankingEntityList = rankingRepository.findAll();
//        // model에 등록하기
//        model.addAttribute("rankinglist", rankingEntityList);
//        return "index";
//    }

}
