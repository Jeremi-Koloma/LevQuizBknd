package com.LevQuiz.LevQuiz.Repositories;

import com.LevQuiz.LevQuiz.Models.Scores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreRepository extends JpaRepository<Scores, Long> {

    //  la méthode pour avoir une liste de score d'un l'utilsateur
    @Query(value = "SELECT * FROM `scores` WHERE scores.app_user_id=:userid ORDER BY score DESC;", nativeQuery = true)
    List<Object> Userscores(@Param("userid") Long userid);

    //  la méthode pour avoir une liste de score d'un l'utilsateur
    @Query(value = "SELECT * FROM `scores` WHERE scores.quiz_id=:quizid;", nativeQuery = true)
    List<Object> getUserScoresByQuiz(@Param("quizid") Long quizid);


}
