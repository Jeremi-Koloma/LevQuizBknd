package com.LevQuiz.LevQuiz.Services;

import com.LevQuiz.LevQuiz.Models.AppUser;
import com.LevQuiz.LevQuiz.Models.Role;
import com.LevQuiz.LevQuiz.Models.UserRole;
import com.LevQuiz.LevQuiz.Repositories.AppUserRepository;
import com.LevQuiz.LevQuiz.Repositories.RoleRepository;
import com.LevQuiz.LevQuiz.Utility.Constants;
import com.LevQuiz.LevQuiz.Utility.EmailConstructor;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service // Pour dire qu'il sagit du service logique
@Transactional
// Cette classe va implémenté notre interface AccountService pour bénéficier les méthodes
@AllArgsConstructor // Pour l'injections des dépendances
public class AccountServiceImpl implements AccountService {
    // On implemente toutes les méthodes

    // Injecter la dépendance pour Cryter le password de l'utilisateur
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // Injectons AppUserRepository pour enregister les infos de user
    private AppUserRepository appUserRepository;

    // Injectons RoleRepository pour enregistrer les Roles
    private RoleRepository roleRepository;


    // Injectons EmailContructor
    private EmailConstructor emailConstructor;

    // Injectons JavaMailSender pour envoie de mail
    private JavaMailSender mailSender;

    @Override // Enregister pour l'utilisateur
    public AppUser saveUser(String firstname,String lastname, String username,String password, String email) {
        // Encoder le mots de passe
        String encryptedPassword = bCryptPasswordEncoder.encode(password);
        AppUser appUser = new AppUser();
        // Lier ses champs à l'utilisateur
        appUser.setFirstname(firstname);
        appUser.setLastname(lastname);
        appUser.setUsername(username);
        appUser.setEmail(email);
        // affecter le mots de passe crypter comme password de l'utilisateur
        appUser.setPassword(encryptedPassword);
        // Vérifier si la date est vide, on l'ajoute une date
        if (appUser.getCreatedDate() == null){
            appUser.setCreatedDate(new Date());
        }

        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(new UserRole(appUser,roleRepository.findRoleByName("USER")));
        appUser.setUserRoles(userRoles);
        // Maintenant Enregister l'utilisateur
        appUserRepository.save(appUser);
        byte[] bytes;
        //
        try {
            bytes = Files.readAllBytes(Constants.TEMP_USER.toPath());
            String fileName = appUser.getId() + ".png";
            Path path = Paths.get(Constants.USER_FOLDER + fileName);
            Files.write(path, bytes);
        }catch (IOException e){
            e.printStackTrace();
        }
        // Mais Envoyé le Random password à l'utilisateur
        mailSender.send(emailConstructor.contructNewUserEmail(appUser,password));
        // On retourne l'utilisateur
        return appUser;
    }

    @Override // implementation la méthode qui va retourné l'utlisateur par son nom
    public AppUser findByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    @Override // implementation de la méthode qui va retouné l'utilisateur grace à non email
    public AppUser findByEmail(String email) {
        return appUserRepository.findByEmail(email);
    }

    @Override // implementation de la méthode qui retourne la liste des utilisateurs
    public List<AppUser> userList() {
        return appUserRepository.findAll();
    }

    @Override // implementation de la méthode qui va retourné un Role par son nom
    public Role findUserRoleByName(String name) {
        return roleRepository.findRoleByName(name);
    }

    @Override  // Ajouter un Rôle à un utilisateur
    public void addNewRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String userName, String name) {
        // Pour l'affecter un rôle à un USER, on le récupère d'abord dans la base de donne;
        AppUser appUser = appUserRepository.findByUsername(userName);
        // On récupère aussi les roles dans la base de donnée
        Role role = roleRepository.findRoleByName(name);
        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(new UserRole(appUser,role));
        // Maintenant on l'affecte un role
        appUser.setUserRoles(userRoles);
        appUserRepository.save(appUser);
    }

    @Override // implementation de la méthode qui enregistre le Role
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void updateUser(AppUser appUser, HashMap<String, String> request) {
        // Récupéré le mots de passe saisi par l'utilisateur
        String password = appUser.getPassword();
        // Encoder le mots de passe
        String encryptedPassword = bCryptPasswordEncoder.encode(password);
        // attrubier le mots de passe encoder comme mots de passe de l'utilisateur
        appUser.setPassword(encryptedPassword);
        // Maintenant enregister les information
        appUserRepository.save(appUser);
    }

    @Override // implementation de la méthode qui va retouné un utilisateur par son ID
    public AppUser findUserById(Long id) {
        return appUserRepository.findUserById(id);
    }

    @Override // implementation de la méthode qui supprime user
    public void deleteUser(AppUser appUser) {
        appUserRepository.delete(appUser);
    }

    @Override
    public void updateUserPassword(AppUser appUser, String newPassword) {
        // Cryper le nouveau mots de passe
        String encryptedPassword = bCryptPasswordEncoder.encode(newPassword);
        // attrubié ce mots de passe encodé comme mots de passe de l'utilisateur
        appUser.setPassword(encryptedPassword);
        // Mais Envoyé le Random password à l'utilisateur
        appUserRepository.save(appUser);
    }

    @Override
    public void resetPassword(AppUser appUser) {
        // Créeons un nouveau mots de passe de type Random
        String password = RandomStringUtils.randomAlphanumeric(10);
        // Cryper le nouveau mots de passe
        String encryptedPassword = bCryptPasswordEncoder.encode(password);
        // attrubié ce mots de passe encodé comme mots de passe de l'utilisateur
        appUser.setPassword(encryptedPassword);
        // Maintenant enregister le l'utilisateur
        appUserRepository.save(appUser);
        // Mais Envoyé le Random password à l'utilisateur
        mailSender.send(emailConstructor.constructResetPasswordEmail(appUser,password));
    }

    @Override // implementation de le méthode qui retourne la liste des nom des utilisateur cas Recherche
    public List<AppUser> getUserListByUsername(String username) {
        return appUserRepository.findByUsernameContaining(username);
    }

    @Override // implementation de la méthode qui va simplement enregistrer l'utilisateur
    public AppUser simpleSave(AppUser appUser) {
       return appUserRepository.save(appUser);
    }
}
