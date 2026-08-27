package com.hisabaty.demo.Student;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotNull;


@Data
public class StudentDTO
{
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "CIN is required")
    @Pattern(regexp = "^[A-Z]{1,2}[0-9]{4,6}$", message = "Invalid Moroccan CIN format")
    private String cin;

    private String phone;
    private String email;

    @NotNull(message = "School ID is required")
    private Long schoolId; // SaaS Rule: Always know which tenant this belongs to!
}