package com.hisabaty.demo.Instructor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Instructor_Patch_Request_DTO {

    @Min(value = 1, message = "ID must be positive")
    private Long schoolId;

    private String name;

    @Pattern(regexp = "^[A-Z]{1,2}[0-9]{4,6}$", message = "Invalid Moroccan CIN format")
    private String cin;

    @Pattern(regexp = "^[0-9]{8,15}$", message = "Invalid phone number format")
    private String phone;

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format")
    private String email;

    private String capNumber;

    private String typeOfLicense;

    private InstructorSpeciality speciality;

    private InstructorStatus status;

    private Double salary;

    private Double hourlyRate;

    @PastOrPresent(message = "Hire date must be in the past or present")
    private LocalDate hireDate;
}
