package com.LevQuiz.LevQuiz.SpringConfig;

import lombok.AllArgsConstructor;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Cette classe va étendre de WebSecurityConfigurerAdapter
@Configuration // Pour dire à srping ou se trouve la classe de configuration
@EnableWebSecurity // Pour activer la sécutiry
@AllArgsConstructor // pour l'injection des dépendances
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // Identifions les routes qui seront accessible sans Authetification
    private static final String[] PUBLIC_MATCHERS = {"/user/login", "/user/register", "/user/registerFormateur", "/user/resetPassword/**", "/image/**"};

    // injectons l'interface UserDetailsService
    private UserDetailsService userDetailsService;

    // injectons BCryptPasswordEncoder pour cryper les mots de passe
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // Cette classe a besion de 02 méthodes pour fonctionner

    // La prémière méthode consiste à utiliser les identifients des utilisateurs vennant dans la base de donnée
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(bCryptPasswordEncoder);
    }


    // La deuxième méthode consiste à prendre toutes les requêtes HTTP entrantes et sortante de votre application ainsi la gestion des Droits
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JwtAuthentication jwtAuthentication = new JwtAuthentication(authenticationManager());
        jwtAuthentication.setFilterProcessesUrl(PUBLIC_MATCHERS[0]);
        //Désactiver le token csrf générer Spring sécurity pour qu'il laisse passer les requêtes
        http.csrf().disable().cors().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests().antMatchers(PUBLIC_MATCHERS).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(jwtAuthentication)
                .addFilterBefore(new JwtAuthorization(), UsernamePasswordAuthenticationFilter.class);
    }


}
