package com.LevQuiz.LevQuiz.Services;

import com.LevQuiz.LevQuiz.Models.AppUser;
import com.LevQuiz.LevQuiz.Models.Notifications;
import com.LevQuiz.LevQuiz.Models.Quiz;
import com.LevQuiz.LevQuiz.Repositories.AppUserRepository;
import com.LevQuiz.LevQuiz.Repositories.NotificationsRepository;
import com.LevQuiz.LevQuiz.Repositories.QuizRepository;
import com.LevQuiz.LevQuiz.Utility.EmailConstructor;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

// Cette classe va implémenté l'interface ServiceQuiz
@Service // Pour dire qu'il sagit du service logique
@Transactional
@AllArgsConstructor // Pour l'injections des dépendances notre QuizRepository
public class QuizServiceImpl implements QuizService{
    // Implementons les méthodes
    // Injectons le RepositoryQuiz
    private QuizRepository quizRepository;



    // Injectons EmailContructor
    private EmailConstructor emailConstructor;

    // Injectons JavaMailSender pour envoie de mail
    private JavaMailSender mailSender;


    // Injectons notre Repository AppUser pour pouvoir assigner un quiz à un apprenant
    private AppUserRepository appUserRepository;

    @Override
    public Quiz saveQuiz(AppUser appUser, HashMap<String, String> request) {
        Quiz quiz = new Quiz();
        String description = request.get("description");
        String titre = request.get("titre");
        quiz.setUsername(appUser.getUsername());
        quiz.setDescription(description);
        quiz.setTitre(titre);
        quiz.setQuizDate(new Date());
        //quiz.setAppUser(appUser); // plus la peine
        quizRepository.save(quiz);
        return quiz;
    }

    @Override // implementation de la méthode pour Afficher tous les Quiz
    public List<Quiz> quizList() {
        return quizRepository.findAll();
    }

    @Override // implementation de la méthode qui va afficher un seul Quiz
    public Quiz getQuizById(Long id) {
        return quizRepository.findQuizById(id);
    }

    @Override
    public Quiz getQuizByTitre(String Titre) {
        return quizRepository.findQuizByTitre(Titre);
    }

    @Override // implementation de la méthode qui va Afficher tous les Quiz créer par un l'utilisateur
    public List<Quiz> findQuizByUsername(String username) {
        return quizRepository.findByUsername(username);
    }

    @Override // implementation de la méthode qui va supprimer un Quiz
    public void deleteQuizById(Long id) {
        quizRepository.deleteById(id);
    }

    @Override // implementation de la méthode qui va permettre d'assigner un quiz à un apprenant
    public Quiz addQuizToUser(Long idQuiz, String username) {
        // Récupérons d'abord le quiz par son id pour pouvoir l'assigner un apprenant
        Quiz quiz= quizRepository.findQuizById(idQuiz);
        // Recuperons l'utilisateur dans son Repository
        AppUser appUser = appUserRepository.findByUsername(username);
        // Maintenant affectons le Quiz à l'utilisateur
        quiz.setUsername(username);
        // une variable pour le message
        String message = username +"! On vous a assigner un nouveau Quiz intutile: "+ quiz.getTitre();
        // Création d'un object notification pour affecter le message à notification
        Notifications notifications = new Notifications();
        notifications.setNotification(message);
        // Recupération de la date d'assignation
        Date date = new Date();
        // Affectons la date d'assignation, à la date de notification
        notifications.setNotificationDate(date);
        notifications.setTitrequiz(quiz.getTitre());
        // Créeons une liste de notification pour avoir une liste de notifications
        List<Notifications> notificationsList = appUser.getNotificationsList();
        notificationsList.add(notifications);
        // maintenant affectons la liste des notification à l'utilisateur
        appUser.setNotificationsList(notificationsList);

        appUserRepository.save(appUser);
        mailSender.send(emailConstructor.constructQuizEmail(appUser,notifications));
        // On l'enregiste maintenant
        return quizRepository.save(quiz);
    }

}
