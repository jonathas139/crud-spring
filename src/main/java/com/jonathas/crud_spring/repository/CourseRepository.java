package com.jonathas.crud_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jonathas.crud_spring.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {


    
}