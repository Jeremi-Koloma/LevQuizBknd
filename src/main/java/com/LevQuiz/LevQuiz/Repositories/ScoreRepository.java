package com.LevQuiz.LevQuiz.Repositories;

import com.LevQuiz.LevQuiz.Models.Scores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends JpaRepository<Scores, Long> {
}
