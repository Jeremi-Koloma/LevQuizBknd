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
        MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper email = new MimeMessageHelper(mimeMessage);
                email.setPriority(1);
                email.setTo(user.getEmail());
                email.setSubject("Bienvenu sur levQuiz");
                email.setText(text, true);
                email.setFrom(new InternetAddress(environment.getProperty("support.email")));
            }
        };
        return messagePreparator;
    }


    //interface MimeMessagePreparator // une méthode qui va envoyé l'email à l'utilisateur quand il change son password
    public MimeMessagePreparator constructResetPasswordEmail(AppUser user, String password){
        // Context de thymleaf, pour lier user et password à notre html
        Context context = new Context();
        context.setVariable("user", user); // Lier le username au context thymleaf
        context.setVariable("password", password); //  Lier le password au context thymleaf
        String text= templateEngine.process("ResetPasswordUserEmailTemplate", context); // on passe le template html et le context à cette varriable text

        // Génération du message
        // Génération du message
        MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper email = new MimeMessageHelper(mimeMessage);
                email.setPriority(1);
                email.setTo(user.getEmail());
                email.setSubject("Bienvenu sur levQuiz");
                email.setText(text, true);
                email.setFrom(new InternetAddress(environment.getProperty("support.email")));
            }
        };
        return messagePreparator;
    }
}
