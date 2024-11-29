package com.jonathas.crud_spring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.jonathas.crud_spring.model.Course;
import com.jonathas.crud_spring.repository.CourseRepository;

@SpringBootApplication
public class CrudSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudSpringApplication.class, args);
	}
	/* 
	@Bean
	CommandLineRunner initDatabse( CourseRepository courseRepository ){
		return args -> {
			courseRepository.deleteAll();

			Course c1 = new Course();
			c1.setName("Fazer Projeto Cleris");
			c1.setCategory("Andamento");
			Course c2 = new Course();
			c2.setName("Projeto Camolesi");
			c2.setCategory("Concluida");
			Course c3 = new Course();
			c3.setName("Projeto Martins");
			c3.setCategory("Pendente");
			
			courseRepository.save(c1);
			courseRepository.save(c2);
			courseRepository.save(c3);
		};
	} */

}
