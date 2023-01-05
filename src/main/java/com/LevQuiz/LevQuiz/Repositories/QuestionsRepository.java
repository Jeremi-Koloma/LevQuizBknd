package com.LevQuiz.LevQuiz.Repositories;

import com.LevQuiz.LevQuiz.Models.Questions;
import org.springframework.data.jpa.repository.JpaRepository;

// Cette classe va étendre de JpaRepository avec EntityName et ID en param pour l'enregister dans la base de donnée
public interface QuestionsRepository extends JpaRepository<Questions, Long> {

}
