package com.LevQuiz.LevQuiz.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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

    private String username;
    private String notification;
    private Date notificationDate;

    // FetchType.LAZY, en chargent une notification, n'affiche pas les utilisateurs
    @OneToMany(fetch = FetchType.LAZY) // une notification peut avoir plusieurs Utilisateurs (1..*)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private List<AppUser> appUserList; // L'utilisateur
}
