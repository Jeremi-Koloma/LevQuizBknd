package com.LevQuiz.LevQuiz.Services;

import com.LevQuiz.LevQuiz.Models.Formateur;

public interface FormateurService {

    // Une methode qui va prendre les informations de formateur en parmètre pour l'Enregistrer
    Formateur saveFormateur(String firstname, String lastname, String username, String password, String email, String specialite, String localite, String entreprise);

}
