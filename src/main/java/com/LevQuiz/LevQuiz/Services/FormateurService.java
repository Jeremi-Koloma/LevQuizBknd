package com.LevQuiz.LevQuiz.Services;

import com.LevQuiz.LevQuiz.Models.Formateur;

import java.util.HashMap;

public interface FormateurService {

    // Une methode qui va prendre les informations de formateur en parmètre pour l'Enregistrer
    Formateur saveFormateur(String firstname, String lastname, String username, String password, String email, String specialite, String localite, String entreprise);


    // Une méthode pour changer le status de user
    // Une méthode pour mèttre l'utilisateur à jours
    Formateur changerStatusFormateur(Long id, Formateur formateur);
}
