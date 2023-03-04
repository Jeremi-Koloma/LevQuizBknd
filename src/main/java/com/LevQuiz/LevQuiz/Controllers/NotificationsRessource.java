package com.LevQuiz.LevQuiz.Controllers;

import com.LevQuiz.LevQuiz.Models.Notifications;
import com.LevQuiz.LevQuiz.Repositories.NotificationsRepository;
import com.LevQuiz.LevQuiz.Services.NotificationsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // Identifier la classe comme étant un controller
@RequestMapping(value = "/notification")
@AllArgsConstructor // pour l'injections des dépendances
public class NotificationsRessource {
    // Injectons le service notification
    private NotificationsService notificationsService;

    // Injectons le repository aussi pour vérifer s'il ya notification ou pas
    private NotificationsRepository notificationsRepository;

    @GetMapping("/listNotifications")
    public ResponseEntity<?> listNotificaion(){
        // vérifier la liste notification est vide
        if (notificationsService.listNotification().isEmpty()){
            return new ResponseEntity<>("Aucune Notification !", HttpStatus.NOT_FOUND);
        }
        // sinon s'il ya de notifications
        return new ResponseEntity<>(notificationsRepository.findAll(), HttpStatus.OK);
    }

    // Une méthode pour supprimer un utilisateur
    @DeleteMapping("/delete/{idNotification}") // La requête (DELETE) Notification;
    public String delete(@PathVariable Long idNotification){
        return notificationsService.supprimer(idNotification); // Qui va retourné le service supprimer;
    }

    @PutMapping("/changeEtatNotification/{id}")
    public Notifications changeEtatNotif(@PathVariable Long id, Notifications notifications){
        notifications.setEtat(false);
        return notificationsService.changerEtatNotification(id, notifications); // Qui va retourné le service modifier;
    }

}
