package com.LevQuiz.LevQuiz.Models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity // Identifier cette classe comme une table dans la base de donnée
@NoArgsConstructor // constructeur sans argument
@AllArgsConstructor // constructeur avce tous les paramètres
@Getter // Génération des getters
@Setter // Génération des getters
public class AppUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 14651065165L;

    @GeneratedValue(strategy = GenerationType.AUTO) // Identifier notre primary Key
    @Id
    @Column(updatable = false, nullable = false) // à revoir après
    private Long id;
    private String firstname;
    private String lastname;

    // Ce champ est unqiue
    @Column(unique = true)
    private String username;

    // pour cachier le mots de passe s'afficher publiquements
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    // Ce champ est unqiue
    @Column(unique = true)
    private String email;

    private Date createdDate;

    // cascade, quand on supprime l'utilisateur, on le supprime avec son Role aussi
    // FetchType.EAGER, en chargent l'utilisateur, Affiche son userRole aussi.
    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL, fetch = FetchType.EAGER) // un utilisateur peut avoir un ou plusieurs userRoles (1..*)
    private Set<UserRole> userRoles = new HashSet<>();

    // cascade, quand on supprime un utilisateur, on le supprime avec ses Notifications aussi
    // FetchType.LAZY, en chargent l'utilisateur, n'affiche pas ses Notifications
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY) // un utilisateur peut avoir plusieurs Notifications vise vers ça
    private List<Notifications> notificationsList;
}
