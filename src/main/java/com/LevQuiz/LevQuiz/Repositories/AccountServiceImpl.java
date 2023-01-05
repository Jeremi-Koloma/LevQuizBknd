package com.LevQuiz.LevQuiz.Repositories;

import com.LevQuiz.LevQuiz.Models.AppUser;
import com.LevQuiz.LevQuiz.Models.Role;
import com.LevQuiz.LevQuiz.Services.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

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


    @Override
    public void saveUser(AppUser appUser) {

    }

    @Override
    public AppUser findByUsername(String username) {
        return null;
    }

    @Override
    public AppUser findByEmail(String email) {
        return null;
    }

    @Override
    public List<AppUser> userList() {
        return null;
    }

    @Override
    public Role findUserRoleByName(String role) {
        return null;
    }

    @Override
    public Role saveRole(Role role) {
        return null;
    }

    @Override
    public void updateUser(AppUser appUser) {

    }

    @Override
    public AppUser findById(Long id) {
        return null;
    }

    @Override
    public void deleteUser(AppUser appUser) {

    }

    @Override
    public void resetPassword(AppUser appUser) {

    }

    @Override
    public List<AppUser> getUserListByUsername(String username) {
        return null;
    }

    @Override
    public void simpleSave(AppUser appUser) {

    }
}
