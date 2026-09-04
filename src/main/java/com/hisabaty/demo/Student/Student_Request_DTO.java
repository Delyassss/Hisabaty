package com.hisabaty.demo.Student;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotNull;


@Data
public class StudentDTO
{
    @NotNull(message = "School ID is required")
    private Long schoolId; // SaaS Rule: Always know which tenant this belongs to!
    
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "CIN is required")
    @Pattern(regexp = "^[A-Z]{1,2}[0-9]{4,6}$", message = "Invalid Moroccan CIN format")
    private String cin;

    @Pattern(regexp = "^[0-9]{8,15}$", message = "Invalid phone number format")
    private String phone;
    
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format")
    private String email;
    
    @NotNull(message = "Type of license is required")
    private String typeOfLicense;
    
    @NotNull(message = "Already passed code is required")
    private Boolean alreadyPassedCode;

    private Double advancePayment;
    private Double remainingPayment;
}