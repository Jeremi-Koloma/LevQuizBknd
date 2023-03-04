package com.LevQuiz.LevQuiz.Services;

import com.LevQuiz.LevQuiz.Models.Notifications;

import java.util.List;

public interface NotificationsService {
    // une méthode pour lister toutes les Notifications
    List<Notifications> listNotification();

    // une méthode qui va retourner une seule Notification
    Notifications getNotification(Notifications notifications);

    // Une méthode va retourné String qui va prendre en param (idNotification) à supprimer; // methode DELECTE
    String supprimer(Long idNotification);

    Notifications changerEtatNotification(Long id, Notifications notifications);
}
