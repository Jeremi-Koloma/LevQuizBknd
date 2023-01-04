package com.LevQuiz.LevQuiz.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity // Identifier cette classe comme une table dans la base de donnée
@NoArgsConstructor // Généré un constructeur sans paramètre
@AllArgsConstructor // Généré un constructeur avec tous les arguments
@Getter // Génération des Getters
@Setter // Génération des Setters
public class Quiz {
    @GeneratedValue (strategy = GenerationType.AUTO) // Notre primary Key
    @Id // Identifier notre id
    @Column(updatable = false, nullable = false) // à revoir après
    private Long id;

    private String titre;
    private String description;
    private Date quizDate;

}
