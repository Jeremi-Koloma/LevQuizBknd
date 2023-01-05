package com.LevQuiz.LevQuiz.Repositories;

import com.LevQuiz.LevQuiz.Models.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

// Cette classe va étendre de JpaRepository avec Quiz en param et ID pour enregister dans la base de donnée
public interface QuizRepository extends JpaRepository<Quiz, Long> {

}
