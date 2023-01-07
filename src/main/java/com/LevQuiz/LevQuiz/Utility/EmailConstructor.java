package com.LevQuiz.LevQuiz.Utility;

import com.LevQuiz.LevQuiz.Models.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Objects;

@Component // pour que la classe exécute
@AllArgsConstructor // Pour injections des dépendances
public class EmailConstructor {
    // Interface
    private Environment environment;

    // La méthode qui va envoyé un mail
    private TemplateEngine templateEngine;

    //interface MimeMessagePreparator // une méthode qui va envoyé l'email à l'utilisateur quand il s'inscrit
    public MimeMessagePreparator contructNewUserEmail(AppUser user, String password){
        // Context de thymleaf, pour lier user et password à notre html
        Context context = new Context();
        context.setVariable("user", user); // Lier le username au context thymleaf
        context.setVariable("password", password); //  Lier le password au context thymleaf
        String text= templateEngine.process("NewUserEmailTemplate", context); // on passe le template html et le context à cette varriable text

        // Génération du message
        return new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                // Définissons des configurations pour envoyé email
                MimeMessageHelper email = new MimeMessageHelper(mimeMessage);
                email.setPriority(1);
                email.setTo(user.getEmail()); // envoyé à l'Email de l'utisateur
                email.setSubject("Bienvenue sur LevQuiz !"); // Sujet de mail
                email.setText(text, true); // Actuel text notre html
                email.setFrom(new InternetAddress(Objects.requireNonNull(environment.getProperty("support.email")))); // Source d'envoie du Mail
            }
        };
    }


    //interface MimeMessagePreparator // une méthode qui va envoyé l'email à l'utilisateur quand il change son password
    public MimeMessagePreparator constructResetPasswordEmail(AppUser user, String password){
        // Context de thymleaf, pour lier user et password à notre html
        Context context = new Context();
        context.setVariable("user", user); // Lier le username au context thymleaf
        context.setVariable("password", password); //  Lier le password au context thymleaf
        String text= templateEngine.process("ResetPasswordUserEmailTemplate", context); // on passe le template html et le context à cette varriable text

        // Génération du message
        return new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                // Définissons des configurations pour envoyé email
                MimeMessageHelper email = new MimeMessageHelper(mimeMessage);
                email.setPriority(1);
                email.setTo(user.getEmail()); // envoyé à l'Email de l'utisateur
                email.setSubject("Reset password LevQuiz !"); // Sujet de mail
                email.setText(text, true); // Actuel text notre html
                email.setFrom(new InternetAddress(Objects.requireNonNull(environment.getProperty("support.email")))); // Source d'envoie du Mail
            }
        };
    }
}
