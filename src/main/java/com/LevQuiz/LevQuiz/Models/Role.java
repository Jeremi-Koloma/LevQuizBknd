package com.LevQuiz.LevQuiz.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity // Identifier cette classe comme une table dans la base de donnée
@NoArgsConstructor // un constructeur sans paramètre
@AllArgsConstructor // un constructeur avec tous les paramètres
@Getter // Génération des getters
@Setter // Génération des setters
public class Role {
    @GeneratedValue (strategy = GenerationType.AUTO) // Notre primary Key
    @Id // identifier notre id
    @Column(updatable = false, nullable = false) // à revoir après
    private Long roleId;

    private String name;

    @OneToMany(mappedBy ="role", cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    private Set<UserRole> userRoles = new HashSet<>();
}
