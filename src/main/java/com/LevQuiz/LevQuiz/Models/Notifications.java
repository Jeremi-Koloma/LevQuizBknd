package com.LevQuiz.LevQuiz.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity // Identifier cette classe comme une table dans la base de donnée
@NoArgsConstructor // un constructeur sans paramètre
@AllArgsConstructor // Un constructeur avec tous les arguments
@Getter // Génération des getters
@Setter // Génération des setters
public class Notifications {
    @GeneratedValue (strategy = GenerationType.AUTO) // Notre primary Key
    @Id // notre id;
    @Column(updatable = false, nullable = false) // à revoir après
    private Long id;

    private String notification;
    private Date notificationDate;

}
