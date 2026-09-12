package com.carlos.apifacturacion.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GenerarHashMain {

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String passwordPlana = "123456";
        String hash = encoder.encode(passwordPlana);
        System.out.println("=================================================");
        System.out.println("Contraseña real: " + passwordPlana);
        System.out.println("Hash generado: " + hash);
        System.out.println("-------------------------------------------------");
        System.out.println("Copia este SQL para tu base de datos:");
        System.out.println("DELETE FROM users WHERE username = 'demo';");
        System.out.println("INSERT INTO users (username, password, enabled) VALUES ('demo', '" + hash + "', true);");
        System.out.println("=================================================");
    }
}