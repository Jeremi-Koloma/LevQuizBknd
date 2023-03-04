package com.LevQuiz.LevQuiz.Services;

import com.LevQuiz.LevQuiz.Models.Notifications;
import com.LevQuiz.LevQuiz.Repositories.NotificationsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service // Pour dire qu'il sagit du service logique
@Transactional
// Cette classe va implementé l'interface NotificationsService pour bénécifier de la méthode save
@AllArgsConstructor // Pour l'injection de la dépendance, NotificationRepository
public class NotificationsServiceImpl implements NotificationsService {
    // Implementons la méthode
    // Injectons le Repository
    public NotificationsRepository notificationsRepository;


    @Override
    public List<Notifications> listNotification() {
        return notificationsRepository.findAll();
    }

    @Override
    public Notifications getNotification(Notifications notifications) {
        notifications.setEtat(true);
        return notificationsRepository.save(notifications);
    }

    @Override
    public String supprimer(Long idNotification) {
        notificationsRepository.deleteNotificationsById(idNotification);
        return "Notification supprimer";
    }

    @Override
    public Notifications changerEtatNotification(Long id, Notifications notifications) {
        return notificationsRepository.findById(id)
                .map(notif->{
                    if (notifications.getTitrequiz() != null){
                        notif.setTitrequiz(notifications.getTitrequiz());
                    }
                    if (notifications.getNotification() != null){
                        notif.setNotification(notifications.getNotification());
                    }
                    if (notifications.getNotificationDate() != null){
                        notif.setNotificationDate(notifications.getNotificationDate());
                    }
                    if (notifications.getEtat() != null){
                        notif.setEtat(notifications.getEtat());
                    }
                    return notificationsRepository.save(notif);
                }).orElseThrow(()-> new RuntimeException("Notification non trouver"));
    }
}
