package com.LevQuiz.LevQuiz.Services;

import com.LevQuiz.LevQuiz.Models.Reponses;

import java.util.List;

public interface ReponseService {
    // Définissions nos méthodes

    // une méthode pour Ajouter une Reponse
    Reponses saveReponse(Reponses reponses, Long idquestion);

    // Une méthode pour Lister les Reponses
    List<Reponses> listReponse();

    // Une méthode pour Afficher une seule Reponse
    Reponses findReponseById(Long id);

    // Une méthode pour Modifier une Reponse
    Reponses updateReponse(Long id, Reponses reponses);

    // Une méthode pour Supprimer une Reponse
    String deleteReponseById(Long id);

}
