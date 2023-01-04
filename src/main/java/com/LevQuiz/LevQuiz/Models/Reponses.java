package com.LevQuiz.LevQuiz.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity // Identifier cette classe comme une table dans la base de donnée
@NoArgsConstructor // Générer un constructeur sans paramètre
@AllArgsConstructor // Générer un construceur avec tous les parmètre
@Getter // Génération des getters
@Setter // Génération des Setters
public class Reponses {
    @GeneratedValue (strategy = GenerationType.AUTO) // notre primary Key
    @Id
    @Column(updatable = false, nullable = false) // à revoir après
    private Long id;

    private String reponse;

    // FetchType.EAGER, en chargent une Reponse, affiche la question
    @ManyToOne(fetch = FetchType.EAGER) //  Plusieurs Reponses à une question (*..1)
    private Questions questions;
}
