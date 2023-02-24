package com.LevQuiz.LevQuiz.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Scores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long score;

    private String username;

    private String quiztire;

    private Date scoreDate;

    @ManyToOne
    @JsonIgnore
    private AppUser appUser;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Quiz quiz;
}
