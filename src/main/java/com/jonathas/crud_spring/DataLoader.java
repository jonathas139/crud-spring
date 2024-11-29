package com.jonathas.crud_spring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.jonathas.crud_spring.domain.user.User;
import com.jonathas.crud_spring.model.Course;
import com.jonathas.crud_spring.repository.CourseRepository;
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
    public CommandLineRunner loadData(CourseRepository courseRepository) {
        return args -> {
            // Verificar se o banco de dados já tem usuários; caso contrário, adicionar um usuário default
            if (userRepository.count() == 0) {
                String hashedPassword = passwordEncoder.encode("admin123");  // Senha encriptada
                User adminUser = new User(
                        null,   // ID será gerado automaticamente
                        "Admin", // Nome do usuário
                        "admin@example.com",  // Email do usuário
                        hashedPassword  // Senha encriptada
                        , null
                );
                userRepository.save(adminUser);
                System.out.println("Usuário Admin adicionado ao banco de dados.");

                //criar 3 cursos para user admin.
                courseRepository.deleteAll();

                Course c1 = new Course();
                c1.setName("Fazer Projeto Cleris");
                c1.setCategory("Andamento");
                c1.setUser(adminUser);

                Course c2 = new Course();
                c2.setName("Projeto Camolesi");
                c2.setCategory("Concluida");
                c2.setUser(adminUser);

                Course c3 = new Course();
                c3.setName("Projeto Martins");
                c3.setCategory("Pendente");
                c3.setUser(adminUser);
                
                courseRepository.save(c1);
                courseRepository.save(c2);
                courseRepository.save(c3);
            }
        };
    }
}