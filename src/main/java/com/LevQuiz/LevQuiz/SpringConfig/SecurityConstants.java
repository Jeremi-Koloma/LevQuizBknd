package com.LevQuiz.LevQuiz.SpringConfig;

public class SecurityConstants {
    // Déclarons des constantes que nous allons utilisé dans JwtAuthentication
    public static final String SECRET = "mySecret231090";
    public static final long EXPIRATION_TIME = 10_1000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_TYPE = "Authorization";
    public static final String CLIENT_DOMAIN_URL = "*";

}
