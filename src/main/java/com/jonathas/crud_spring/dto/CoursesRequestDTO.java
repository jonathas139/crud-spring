package com.jonathas.crud_spring.dto;

public record CoursesRequestDTO (String name, String category, String token) {

    public String getName() {
        return this.name;
    }

    public String getCategory() {
        return this.category;
    }
    
}
