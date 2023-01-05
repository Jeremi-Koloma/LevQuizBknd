package com.LevQuiz.LevQuiz.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Entity // Identifier cette classe comme une table dans la base de donnée
@NoArgsConstructor // Générer un constructeur sans paramètre
@AllArgsConstructor // Générer un constructeur avec tous les paramètre
@Getter // Génération des Getters
@Setter // Génération des setters
public class Questions implements Serializable {
    @Serial
    private static final long serialVersionUID = 2491065L;

    @GeneratedValue (strategy = GenerationType.AUTO) // Identifier notre primary Key
    @Id // Identifier notre id
    @Column(updatable = false, nullable = false) // à revoir après
    private Long id;
    private String question;

    // FetchType.EAGER, en chargent une Question, affiche le Quiz
    @ManyToOne(fetch = FetchType.EAGER) // Plusieurs Questions peuvent appartenir à un seul Quiz (*..1)
    private Quiz quiz;
}
