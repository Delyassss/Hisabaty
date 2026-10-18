package com.hisabaty.demo.Instructor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Instructor_Request_DTO {

    @NotNull(message = "School ID is required")
    @Min(value = 1, message = "ID must be positive")
    private Long schoolId;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "CIN is required")
    @Pattern(regexp = "^[A-Z]{1,2}[0-9]{4,6}$", message = "Invalid Moroccan CIN format")
    private String cin;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^[0-9]{8,15}$", message = "Invalid phone number format")
    private String phone;

    @NotBlank(message = "Email is required")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format")
    private String email;

    private String capNumber;

    @NotBlank(message = "Type of license is required")
    private String typeOfLicense;

    private InstructorSpeciality speciality;

    private InstructorStatus status;

    private Double salary;

    private Double hourlyRate;

    @PastOrPresent(message = "Hire date must be in the past or present")
    private LocalDate hireDate;

    private LocalDateTime createdAt;
}
