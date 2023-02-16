package com.LevQuiz.LevQuiz.Repositories;

import com.LevQuiz.LevQuiz.Models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

// Cette classe va étendre de l'interface JpaRepository pour avoir accès à la base de donnée
@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    // Une méthode pour trouver un utilisateur par son Username
    AppUser findByUsername(String username);

    // Une méthode qui va permettre de retrouver un utilisateur par son email.
    AppUser findByEmail(String email);

    // une méthode qui va permettre de retourner un utilisateur par son id
    AppUser findUserById(Long id);

    // Retourner une liste des utilisateurs pour un champs de recherche l'utilisateur
    List<AppUser> findByUsernameContaining(String username);


    //  la méthode qui affiche uniquement les Apprenants
    @Query(value = "SELECT app_user.*,role.* FROM app_user,role,user_role WHERE app_user.id = user_role.user_id and role.role_id = user_role.role_id and role.name=\"APPRENANT\";", nativeQuery = true)
    List<Object> studentList();


    //  la méthode qui affiche uniquement les Formateurs
    @Query(value = "SELECT app_user.*,role.*,formateur.localite,formateur.entreprise,formateur.specialite FROM app_user,role,user_role,formateur WHERE app_user.id = user_role.user_id and role.role_id = user_role.role_id AND formateur.id=app_user.id and role.name=\"FORMATEUR\";", nativeQuery = true)
    List<Object> formateurList();


    //  la méthode qui affiche uniquement les Formateurs dont leur status n'est pas valider
    @Query(value = "SELECT app_user.id,app_user.firstname,app_user.lastname,formateur.status FROM `app_user`,formateur WHERE app_user.id=formateur.id and formateur.status = false ORDER BY app_user.created_date DESC;", nativeQuery = true)
    List<Object> formateurListNoActive();

}
