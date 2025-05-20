package com.example.bullsandcows.Repository;

import com.example.bullsandcows.Entity.Ranking;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface RankingRepository  extends CrudRepository<Ranking, Long> {
    @Override
    ArrayList<Ranking> findAll();

    // 시도 횟수 오름차순
    List<Ranking> findAllByOrderByTryCountAsc();

    Optional<Ranking> findByUserID(String userID);
}
