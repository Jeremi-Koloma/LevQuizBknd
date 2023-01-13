package com.LevQuiz.LevQuiz.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.coyote.Response;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Entity // Identifier cette classe comme une table dans la base de donnée
@NoArgsConstructor // Générer un constructeur sans paramètre
@AllArgsConstructor // Générer un constructeur avec tous les paramètre
@Getter // Génération des Getters
@Setter // Génération des setters
public class Questions implements Serializable {
    @Serial
    private static final long serialVersionUID = 2491065L;

    @GeneratedValue (strategy = GenerationType.IDENTITY) // Identifier notre primary Key
    @Id // Identifier notre id
    @Column(updatable = false, nullable = false) // à revoir après
    private Long id;

    private String question;
    private Long points;
    private Long duree;

    // FetchType.EAGER, en chargent une Question, affiche le Quiz
    //CascadeType.ALL , en supprimant un Quiz, on le supprime avec toutes ses question

    @ManyToOne(fetch = FetchType.EAGER) // Plusieurs Questions peuvent appartenir à un seul Quiz (*..1)
    @JsonIgnore
    private Quiz quiz;

    @OneToMany(mappedBy = "questions", cascade = CascadeType.ALL)
    List<Reponses> responseList;

}
