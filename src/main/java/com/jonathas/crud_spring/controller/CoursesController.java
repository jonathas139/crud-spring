package com.jonathas.crud_spring.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jonathas.crud_spring.model.Course;
import com.jonathas.crud_spring.repository.CourseRepository;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/courses")
@AllArgsConstructor
public class CoursesController {
    
    private final CourseRepository courseRepository;

    @GetMapping
    public List<Course> list(){
        return courseRepository.findAll();
    }


}
