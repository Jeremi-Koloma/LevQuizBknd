package com.LevQuiz.LevQuiz.Services;

import com.LevQuiz.LevQuiz.Models.Formateur;
import com.LevQuiz.LevQuiz.Models.UserRole;
import com.LevQuiz.LevQuiz.Repositories.FormateurRepository;
import com.LevQuiz.LevQuiz.Repositories.RoleRepository;
import com.LevQuiz.LevQuiz.Utility.Constants;
import com.LevQuiz.LevQuiz.Utility.EmailConstructor;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

// Cette classe va implementer le ServiceFormateur

@Service // Pour dire qu'il sagit du service logique
@Transactional
// Cette classe va implémenté notre interface AccountService pour bénéficier les méthodes
@AllArgsConstructor // Pour l'injections des dépendances
public class FormateurServiceImpl implements FormateurService{

    // Injecter la dépendance pour Cryter le password de l'utilisateur
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // Injectons EmailContructor
    private EmailConstructor emailConstructor;

    // Injectons JavaMailSender pour envoie de mail
    private JavaMailSender mailSender;

    // Injectons RoleRepository pour enregistrer les Roles
    private RoleRepository roleRepository;

    // Injectons le FormateurRepository
    private FormateurRepository formateurRepository;



    // implementons les méthodes

    @Override
    public Formateur saveFormateur(String firstname, String lastname, String username, String password, String email, String specialite, String localite, String entreprise) {

        // Une variable pour Encoder le mots de passe
        String encryptedPassword = bCryptPasswordEncoder.encode(password);

        // Créons une instance de Fomateur
        Formateur formateur = new Formateur();

        //Lions les champs de paramètre à notre formateur
        formateur.setFirstname(firstname);
        formateur.setLastname(lastname);
        formateur.setUsername(username);
        // Donons le mots de passe crypter au formateur lors de enregistrement
        formateur.setPassword(encryptedPassword);
        formateur.setEmail(email);
        formateur.setSpecialite(specialite);
        formateur.setLocalite(localite);
        formateur.setEntreprise(entreprise);
        // Maintons le status à false
        formateur.setStatus(false);
        // Vérifier si la date de creation est vide, on l'ajoute une date
        if (formateur.getCreatedDate() == null){
            formateur.setCreatedDate(new Date());
        }

        // Affectons le Role FORMATEUR au formateur
        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(new UserRole(formateur, roleRepository.findRoleByName("FORMATEUR")));
        // attribuer ce role au Formateur
        formateur.setUserRoles(userRoles);

        // Maintenant Enregister le formateur
        formateurRepository.save(formateur);

        // Affectons une photo de profil par défaut
        byte[] bytes;

        try {
            bytes = Files.readAllBytes(Constants.TEMP_USER.toPath());
            String fileName = formateur.getId() + ".png";
            Path path = Paths.get(Constants.USER_FOLDER + fileName);
            Files.write(path, bytes);
        }catch (IOException e){
            e.printStackTrace();
        }

        return formateur;
    }

    // Implementation de la méthode qui change le status de formateur
    @Override
    public Formateur changerStatusFormateur(Long id, Formateur formateur) {
        return formateurRepository.findById(id) // S'il trouve l'ID;
                .map(formt->{  // On fait du mappage;
                    if (formateur.getFirstname() != null){
                        formt.setFirstname(formateur.getFirstname());
                    }

                    if (formateur.getLastname() != null) {
                        formt.setLastname(formateur.getLastname());
                    }

                    if (formateur.getUsername() != null){
                        formt.setUsername(formateur.getUsername());
                    }

                    if (formateur.getEmail() != null){
                        formt.setEmail(formateur.getEmail());
                    }

                    if (formateur.getSpecialite() != null){
                        formt.setSpecialite(formateur.getSpecialite());
                    }

                    if (formateur.getLocalite() != null){
                        formt.setLocalite(formateur.getLocalite());
                    }

                    if (formateur.getEntreprise() != null){
                        formt.setEntreprise(formateur.getEntreprise());
                    }

                    if (formateur.getStatus() != null){
                        formt.setStatus(formateur.getStatus());
                    }
                    return formateurRepository.save(formt);
                }).orElseThrow(()-> new RuntimeException("Formateur non trouver"));
    }


}
