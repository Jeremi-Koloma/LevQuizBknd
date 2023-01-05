package com.LevQuiz.LevQuiz.Utility;

import com.LevQuiz.LevQuiz.Models.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Objects;

@AllArgsConstructor // Pour Activer Environnent
public class EmailConstructor  {
    // Injecter l'interface Environment avoir accès aux variables
    private Environment environment;

    // Pour le templat
    private TemplateEngine templateEngine;

    // Une méthode qui va envoyer des Emails
    public MimeMessagePreparator contructNewUserEmail(AppUser user, String password){
        Context context = new Context();
        context.setVariable("user", user);
        context.setVariable("password", password );

        // Définisson le processus du templat dans le dossier templat qui va contenir le corps du message
        String text = templateEngine.process("newUserEmailTemplate", context);

        MimeMessagePreparator messagePreparator = new MimeMessagePreparator(){
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper email = new MimeMessageHelper(mimeMessage);
                email.setPriority(1);
                email.setTo(user.getEmail()); // Email de l'utilisateur
                email.setSubject("Bienvenu sur LevQuiz !"); // Sujet de l'Email
                email.setText(text, true); // On envoie le corp du mail
                email.setFrom(new InternetAddress(Objects.requireNonNull(environment.getProperty("support.email")))); // notre app.properties
            }
        };
        // On retourne l'email
        return messagePreparator;
    }
}
