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
@NoArgsConstructor // un constructeur sans paramètre
@AllArgsConstructor // Générer un constructeur avec tous les paramètres
@Getter // Génération des Getters
@Setter // Générations des Setters
public class UserRole implements Serializable {
    @Serial
    private static final long serialVersionUID = 8551651L;

    @GeneratedValue (strategy = GenerationType.AUTO) // Notre primary Key
    @Id // identifier notre id;
    @Column(updatable = false, nullable = false) // à revoir après
    private Long userRoleId;

    @ManyToOne() // Plusieurs rôles d'utilisateur peuvent appartenir à un seul utilisateur
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private AppUser appUser; // L'utilisateur

    // FetchType.EAGER, en chargent UserRole, Affiche son Role aussi.
    @ManyToOne(fetch = FetchType.EAGER) // Plusieurs rôles d'utilisateur peuvent appartenir à un seul Role
    private  Role role; // Role


    public UserRole(long userRoleId,AppUser appUser, Role role) {
        this.userRoleId=userRoleId;
        this.appUser=appUser;
        this.role=role;
    }
    public UserRole(AppUser appUser, Role role) {
        this.appUser=appUser;
        this.role=role;
    }
}
