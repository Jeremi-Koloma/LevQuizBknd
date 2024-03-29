package com.LevQuiz.LevQuiz.Services;

import com.LevQuiz.LevQuiz.Models.AppUser;
import com.LevQuiz.LevQuiz.Models.Notifications;
import com.LevQuiz.LevQuiz.Models.Quiz;
import com.LevQuiz.LevQuiz.Repositories.AppUserRepository;
import com.LevQuiz.LevQuiz.Repositories.QuizRepository;
import com.LevQuiz.LevQuiz.Utility.Constants;
import com.LevQuiz.LevQuiz.Utility.EmailConstructor;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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
    public Quiz saveQuiz(AppUser appUser, HashMap<String, String> request, String quizImageName) {
        String description = request.get("description");
        String titre = request.get("titre");
        Quiz quiz = new Quiz();
        quiz.setUsername(appUser.getUsername());
        quiz.setDescription(description);
        quiz.setTitre(titre);
        quiz.setImageName(quizImageName);
        quiz.setQuizDate(new Date());
        quiz.setImagequizid(appUser.getId());
        appUser.setQuizList(quiz);
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

    @Override // une méthode pour avoir un quiz par son titre pour eviter d'avoir deux quiz de meme nom
    public Quiz getQuizByTitre(String Titre) {
        return quizRepository.findQuizByTitre(Titre);
    }

    @Override // implementation de la méthode qui va Afficher tous les Quiz créer par un l'utilisateur
    public List<Quiz> findQuizByUsername(String username) {
        return quizRepository.findByUsername(username);
    }

   /* @Override  implementation de la méthode qui va supprimer un Quiz
    public void deleteQuizById(Long id) {
        quizRepository.deleteById(id);
    }*/

    @Override
    public void deleteQuiz(Quiz quiz) {
        // En supprimant un quiz, on supprime aussi l'image dans le dossier
        try {
            Files.deleteIfExists(Paths.get(Constants.QUIZ_FOLDER + "/" + quiz.getImageName() + ".png"));
            quizRepository.deleteQuizById(quiz.getId());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override // implementaton de la méthode qui permet d'ajouté une image à quiz
    public void saveQuizImage(HttpServletRequest request, String fileName) {
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
        MultipartFile multipartFile = multipartHttpServletRequest .getFile(iterator.next());
        try {
            //assert multipartFile != null; // A efface si la photo ne marche pas
            byte[] bytes = multipartFile.getBytes();
            Path path = Paths.get(Constants.QUIZ_FOLDER + fileName + ".png");
            Files.write(path,bytes, StandardOpenOption.CREATE);
        }catch (Exception e){
        }
    }

    @Override // implementation de la méthode qui va permettre d'assigner un quiz à un apprenant
    public void addQuizToUser(Long idQuiz, String username) {
        // Récupérons d'abord le quiz par son id pour pouvoir l'assigner un apprenant
        Quiz quiz= quizRepository.findQuizById(idQuiz);
        // Recuperons l'utilisateur dans son Repository
        AppUser appUser = appUserRepository.findByUsername(username);
        // Maintenant affectons le Quiz à l'utilisateur
        quiz.setUsername(username);
        // une variable pour le message
        String message = "Bonjour "+username+" ! Votre formateur vous a assigné un nouveau Quiz";
        // Création d'un object notification pour affecter le message à notification
        Notifications notifications = new Notifications();
        notifications.setNotification(message);
        // Recupération de la date d'assignation
        Date date = new Date();
        // Affectons la date d'assignation, à la date de notification
        notifications.setNotificationDate(date);
        notifications.setTitrequiz(quiz.getTitre());
        notifications.setEtat(true);
        // Créeons une liste de notification pour avoir une liste de notifications
        List<Notifications> notificationsList = appUser.getNotificationsList();
        notificationsList.add(notifications);
        // maintenant affectons la liste des notification à l'utilisateur
        appUser.setNotificationsList(notificationsList);

        appUserRepository.save(appUser);
        mailSender.send(emailConstructor.constructQuizEmail(appUser,notifications));
        // On l'enregiste maintenant
        quizRepository.save(quiz);
    }

}
