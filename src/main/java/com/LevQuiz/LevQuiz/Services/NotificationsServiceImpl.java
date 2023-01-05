package com.LevQuiz.LevQuiz.Services;

import com.LevQuiz.LevQuiz.Models.Notifications;
import com.LevQuiz.LevQuiz.Repositories.NotificationsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service // Pour dire qu'il sagit du service logique
@Transactional
// Cette classe va implementé l'interface NotificationsService pour bénécifier de la méthode save
@AllArgsConstructor // Pour l'injection de la dépendance, NotificationRepository
public class NotificationsServiceImpl implements NotificationsService {
    // Implementons la méthode
    // Injectons le Repository
    public NotificationsRepository notificationsRepository;
    @Override
    public void saveNotifications(Notifications notifications) {
        notificationsRepository.save(notifications);
    }

}
