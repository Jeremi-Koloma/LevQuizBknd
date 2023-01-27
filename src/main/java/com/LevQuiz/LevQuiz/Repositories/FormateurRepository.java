package com.LevQuiz.LevQuiz.Repositories;

import com.LevQuiz.LevQuiz.Models.Formateur;
import org.springframework.data.jpa.repository.JpaRepository;

// On étant de l'interface JpaRepository
public interface FormateurRepository extends JpaRepository<Formateur, Long> {

}
