package com.jonathas.crud_spring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.jonathas.crud_spring.domain.user.User;
import com.jonathas.crud_spring.repository.UserRepository;

@Configuration
public class DataLoader {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();  // BCryptPasswordEncoder para hash de senhas
    }

    // Definir o PasswordEncoder como Bean
    @Bean
    public PasswordEncoder passwordEncoderLoader() {
        return new BCryptPasswordEncoder();
    }

    // Carregar dados de usuário padrão
    @Bean
    public CommandLineRunner loadData() {
        return args -> {
            // Verificar se o banco de dados já tem usuários; caso contrário, adicionar um usuário default
            if (userRepository.count() == 0) {
                String hashedPassword = passwordEncoder.encode("admin123");  // Senha encriptada
                User adminUser = new User(
                        null,   // ID será gerado automaticamente
                        "Admin", // Nome do usuário
                        "admin@example.com",  // Email do usuário
                        hashedPassword  // Senha encriptada
                );
                userRepository.save(adminUser);
                System.out.println("Usuário Admin adicionado ao banco de dados.");
            }
        };
    }
}