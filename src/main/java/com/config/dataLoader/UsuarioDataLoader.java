package com.config.dataLoader;

import com.github.javafaker.Faker;
import com.model.Usuario;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.Locale;

@Configuration
public class UsuarioDataLoader {

    @Order(1)
    @Bean
    CommandLineRunner initUsuario(){
        return args -> {
            Faker faker = new Faker(Locale.forLanguageTag("pt-BR"));
            Usuario usuario = new Usuario();

            usuario.setUsername(faker.name().firstName());
            usuario.setRole("PROFESSOR");
            usuario.setPassword(faker.internet().password(8,12));
        };
    }

}
