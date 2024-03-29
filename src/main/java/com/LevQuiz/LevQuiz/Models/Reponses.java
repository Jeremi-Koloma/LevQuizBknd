package com.LevQuiz.LevQuiz.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Entity // Identifier cette classe comme une table dans la base de donnée
@NoArgsConstructor // Générer un constructeur sans paramètre
@AllArgsConstructor // Générer un construceur avec tous les parmètre
@Getter // Génération des getters
@Setter // Génération des Setters
public class Reponses implements Serializable {
    @Serial
    private static final long serialVersionUID = 5115165L;

    @GeneratedValue (strategy = GenerationType.IDENTITY) // notre primary Key
    @Id
    @Column(updatable = false, nullable = false) // à revoir après
    private Long id;

    private String reponse;

    private Boolean iscorrect;

    // FetchType.EAGER, en chargent une Reponse, affiche la question
    @ManyToOne //  Plusieurs Reponses à une question (*..1)
    @JsonIgnore
    private Questions questions;
}
