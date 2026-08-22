package com.hisabaty.demo.Student;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import jakarta.validation.constraints.Pattern;

@Data
public class StudentDTO
{
    @NotBlank(message = "Name is required")
    String name;
    @NotBlank(message = "Phone is required")
    @Column(name = "phone", length = 20)
    String phone;
    @Column(name = "cin", unique = true, nullable = false)
    @NotBlank(message = "Cin is required")
    @Pattern(regexp = "^[0-9]{8}$", message = "CIN must be 8 digits")
    String cin;
    @Column(name = "email", unique = true, length = 100)
    String email;
    Double advancePayment = 0.0;       // How much did they pay today? (e.g., 500 DH)
    Boolean hasMedicalFolder = false;  // Did they bring their physical dossier?
    Boolean alreadyPassedCode = false;
}