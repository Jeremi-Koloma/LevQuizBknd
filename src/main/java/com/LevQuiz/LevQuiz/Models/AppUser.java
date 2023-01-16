package com.LevQuiz.LevQuiz.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity // Identifier cette classe comme une table dans la base de donnée
public class AppUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 14651065165L;

    @GeneratedValue(strategy = GenerationType.IDENTITY) // Identifier notre primary Key
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

    // Rélation pour jouer à un Quiz
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "jeux",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_quiz"))

    private List<Quiz> quizList;

    // constructeur sans argument
    public AppUser() {
    }

    // constructeur avce tous les paramètres
    public AppUser(Long id, String firstname, String lastname, String username, String password, String email, Date createdDate, Set<UserRole> userRoles, List<Notifications> notificationsList, List<Quiz> quizList) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.createdDate = createdDate;
        this.userRoles = userRoles;
        this.notificationsList = notificationsList;
        this.quizList = quizList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public List<Notifications> getNotificationsList() {
        return notificationsList;
    }

    public void setNotificationsList(List<Notifications> notificationsList) {
        this.notificationsList = notificationsList;
    }

    public List<Quiz> getQuizList() {
        return quizList;
    }

    public void setQuizList(Quiz quizList) {
        this.quizList.add(quizList);
    }
}
