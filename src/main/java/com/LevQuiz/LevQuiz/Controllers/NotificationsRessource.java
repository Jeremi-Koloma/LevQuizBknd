package com.LevQuiz.LevQuiz.Controllers;

import com.LevQuiz.LevQuiz.Repositories.NotificationsRepository;
import com.LevQuiz.LevQuiz.Services.NotificationsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    //
}
