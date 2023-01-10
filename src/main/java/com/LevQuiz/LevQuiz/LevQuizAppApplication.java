package com.LevQuiz.LevQuiz;

import com.LevQuiz.LevQuiz.Models.AppUser;
import com.LevQuiz.LevQuiz.Models.Role;
import com.LevQuiz.LevQuiz.Models.UserRole;
import com.LevQuiz.LevQuiz.Repositories.AppUserRepository;
import com.LevQuiz.LevQuiz.Repositories.RoleRepository;
import com.LevQuiz.LevQuiz.Services.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;


@SpringBootApplication
@AllArgsConstructor
public class LevQuizAppApplication {

	// Pour l'injection de notre Repository Role pour vérifier la table
	private RoleRepository roleRepository;

	// Pour l'injection de notre AppUserRepository;
	private final AppUserRepository appUserRepository;

	@Bean // Bean pour que cette fonction exécute pour crypter le mots de passe
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(LevQuizAppApplication.class, args);
	}

	// Au démarrage de l'application, on va ajouter quelque users
	@Bean // pour que lamba execute cette méthode
	CommandLineRunner start(AccountService accountService) {
		// on retourne une expréssion lamba qui va exsécuter lors du démarrage
		return args -> {
			// Vérifier si la table Rôle est vide dans la base de donnée
			if (roleRepository.findAll().size() == 0) {
				// Si c'est vide, on ajoute deux Rôles USER, ADMIN
				accountService.addNewRole(new Role(null, "USER"));
				accountService.addNewRole((new Role(null, "ADMIN")));
			};

			// Ajoutons un Admin à la fois User par défaut
			if (appUserRepository.findAll().size() == 0){
				// Ajoutons un Admin par defaut
				UserRole userRole=new UserRole();
				String pw=bCryptPasswordEncoder().encode("1234");
				accountService.simpleSave(new AppUser(null, "Jeremi", "KOLOMA", "jk.koloma", pw, "kolomajeremi60@gmail.com", new Date(),null, null));

				// Affectons des rôles aux deux à l'Admin ajouter
				accountService.addRoleToUser("jk.koloma", "USER");
				accountService.addRoleToUser("jk.koloma", "ADMIN");
			}

		};
	}

}
