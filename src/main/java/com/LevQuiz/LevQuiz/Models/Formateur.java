package com.LevQuiz.LevQuiz.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
// Heritons la comme mère
public class Formateur extends AppUser {

    private String specialite;

    private String localite;

    private String entreprise;

    private Boolean status;

}
