package com.LevQuiz.LevQuiz.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity // Identifier cette classe comme une table dans la base de donnée
@NoArgsConstructor // Généré un constructeur sans paramètre
@AllArgsConstructor // Généré un constructeur avec tous les arguments
@Getter // Génération des Getters
@Setter // Génération des Setters
public class Quiz implements Serializable {
    @Serial
    private static final long serialVersionUID = 7863165L;

    @GeneratedValue (strategy = GenerationType.IDENTITY) // Notre primary Key
    @Id // Identifier notre id
    @Column(updatable = false, nullable = false) // à revoir après
    private Long id;

    private String username;
    private String titre;
    private String description;
    private Date quizDate;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    List<Questions> questionsList;


    // ****************************    Plus la peine pour la liaison car seule Username peut Gérér    ************************

    // FetchType.EAGER, en chargent le Quiz, affiche l'utilisateur aussi
    //@ManyToOne(fetch = FetchType.EAGER) // Plusieurs Quiz peuvent être créer par un seul utilisateur (*..1)
    //private AppUser appUser;

}
