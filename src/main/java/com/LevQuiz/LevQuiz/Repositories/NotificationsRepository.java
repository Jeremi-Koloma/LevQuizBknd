package com.LevQuiz.LevQuiz.Repositories;

import com.LevQuiz.LevQuiz.Models.Notifications;
import com.LevQuiz.LevQuiz.Models.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

// Cette classe va étendre de JpaRepository avec EntityName et ID en param pour l'enregister dans la base de donnée
@Repository
public interface NotificationsRepository extends JpaRepository<Notifications, Long> {
    // Une méthode qui va retourné une liste de notifications par odre recent
    @Query(value = "SELECT * FROM notifications ORDER BY notification_date DESC;", nativeQuery = true)
    List<Notifications> findAll();
}
