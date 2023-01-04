package com.LevQuiz.LevQuiz.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity // Identifier cette classe comme une table dans la base de donnée
@NoArgsConstructor // constructeur sans argument
@AllArgsConstructor // constructeur avce tous les paramètres
@Getter // Génération des getters
@Setter // Génération des getters
public class AppUser {
    @GeneratedValue(strategy = GenerationType.AUTO) // Identifier notre primary Key
    @Id
    @Column(updatable = false, nullable = false) // à revoir après
    private Long id;

    private String username;

    private String password;

    // Ce champ est unqiue
    @Column(unique = true)
    private String email;

    private Date createdDate;

    // cascade, quand on supprime l'utilisateur, on le supprime avec son Role aussi
    // FetchType.EAGER, en chargent l'utilisateur, Affiche son userRole aussi.
    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL, fetch = FetchType.EAGER) // un utilisateur peut avoir un ou plusieurs userRoles (1..*)
    private Set<UserRole> userRoles = new HashSet<>();

    // cascade, quand on supprime l'utilisateur, on le supprime avec ses Quiz aussi
    // FetchType.LAZY, en chargent l'utilisateur, n'affiche pas ses Quiz.
    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY) // un utilisateur peut créer un ou plusieurs Quiz (1..*)
    private List<Quiz> quizList;

    // cascade, quand on supprime un utilisateur, on le supprime avec ses Notifications aussi
    // FetchType.LAZY, en chargent l'utilisateur, n'affiche pas ses Notifications
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY) // un utilisateur peut avoir plusieurs Notifications (1..*)
    private List<Notifications> notificationsList;
}
