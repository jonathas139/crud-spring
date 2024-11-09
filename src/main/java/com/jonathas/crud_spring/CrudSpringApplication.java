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

	@Bean
	CommandLineRunner initDatabse( CourseRepository courseRepository ){
		return args -> {
			courseRepository.deleteAll();

			Course c = new Course();
			c.setName("Angular com Spring");
			c.setCategory("Front-End");
			
			courseRepository.save(c);
		};
	}

}
