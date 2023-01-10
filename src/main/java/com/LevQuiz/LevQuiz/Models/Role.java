package com.LevQuiz.LevQuiz.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity // Identifier cette classe comme une table dans la base de donnée// un constructeur avec tous les paramètres
@Getter // Génération des getters
@Setter
@NoArgsConstructor // Génération des setters
@AllArgsConstructor
public class Role implements Serializable {
    @Serial
    private static final long serialVersionUID = 4165165L;

    @GeneratedValue (strategy = GenerationType.IDENTITY) // Notre primary Key
    @Id // identifier notre id
    @Column(updatable = false, nullable = false) // à revoir après
    private Long roleId;

    private String name;

    @OneToMany(mappedBy ="role", cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    @JsonIgnore
    private Set<UserRole> userRoles = new HashSet<>();

    public Role(Long roleId, String name) {
        this.roleId=roleId;
        this.name=name;
    }
}
