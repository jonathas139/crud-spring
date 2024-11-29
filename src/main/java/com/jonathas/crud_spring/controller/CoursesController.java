package com.jonathas.crud_spring.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jonathas.crud_spring.domain.user.User;
import com.jonathas.crud_spring.dto.CoursesRequestDTO;
import com.jonathas.crud_spring.infra.security.TokenService;
import com.jonathas.crud_spring.model.Course;
import com.jonathas.crud_spring.repository.CourseRepository;
import com.jonathas.crud_spring.repository.UserRepository;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/courses")
@AllArgsConstructor
public class CoursesController {
    
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final TokenService tokenService;  // Injeção do TokenService
    
    @GetMapping
    public List<Course> list(){
        return courseRepository.findAll();
    }

    @GetMapping("/find/{user_id}")
    public List<Course> list(@PathVariable("user_id") Long user_id){
        return courseRepository.findByUserId(user_id);
    }


    @GetMapping("/{id}")
    
    public ResponseEntity<Course> findaById(@PathVariable Long id){
        return courseRepository.findById(id)
        .map(recordFound -> ResponseEntity.ok().body(recordFound))
        .orElse(ResponseEntity.notFound().build());
    }
    /* 
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Course create(@RequestBody Course course){
        return courseRepository.save(course);
    }*/

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<Course> create(@RequestBody CoursesRequestDTO body, @RequestHeader("Authorization") String authorizationHeader) {
        // Extrair o token do cabeçalho Authorization
        String token = authorizationHeader.replace("Bearer ", "");
        System.out.println("Token recebido: " + token);
        
        // Verificar o token e obter o ID do usuário
        String userEmail = tokenService.getUserFromToken(token);
        System.out.println("Email do usuário extraído do token: " + userEmail);
        
        // Buscar o usuário no banco de dados
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        System.out.println("Usuário encontrado: " + user);
        
        // Exibir detalhes específicos do usuário
        System.out.println("ID do usuário: " + user.getId());
        System.out.println("Email do usuário: " + user.getEmail());
        
        // Criar um novo curso e associá-lo ao usuário autenticado
        Course course = new Course();
        course.setName(body.getName());  // Preencher o curso com o DTO
        course.setCategory(body.getCategory());
        course.setUser(user);
        System.out.println("Novo curso criado: " + course);
        
        // Salvar o curso no banco de dados
        Course savedCourse = courseRepository.save(course);
        
        // Verificar se o curso foi salvo corretamente
        System.out.println("Curso salvo: " + savedCourse);
        
        // Exibir o nome e a categoria do curso salvo
        System.out.println("Curso nome: " + savedCourse.getName());
        System.out.println("Curso categoria: " + savedCourse.getCategory());
        
        // Retornar o curso salvo
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCourse);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Course> update(@PathVariable Long id, @RequestBody CoursesRequestDTO body, @RequestHeader("Authorization") String authorizationHeader){
        return courseRepository.findById(id)
        .map(recordFound -> {
            recordFound.setName(body.getName());
            recordFound.setCategory(body.getCategory());
            Course updated = courseRepository.save(recordFound);
            return ResponseEntity.ok().body(updated);
        })
        .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        return courseRepository.findById(id)
        .map(recordFound -> {
            courseRepository.deleteById(id);
            return ResponseEntity.noContent().<Void>build();
        })
        .orElse(ResponseEntity.notFound().build());
    }

}
