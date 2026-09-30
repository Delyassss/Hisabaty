package com.hisabaty.demo.Student;

import jakarta.persistence.Column;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
public class Student_Request_DTO 
{


    @NotNull(message = "School ID is required")
    @Min(value = 1 , message = "ID must be positive")
    private Long schoolId; // SaaS Rule: Always know which tenant this belongs to!
    
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
    
    @NotBlank(message = "Type of license is required")
    private String typeOfLicense;
    
    @NotNull(message = "Already passed code is required")
    private Boolean alreadyPassedCode;

    private Double advancePayment;
    private Double remainingPayment;

    private Status status ;

    private AttendanceStatus attendanceStatus ;

    @FutureOrPresent(message = "Exam date must be a from now on ")
    private LocalDate examDate ;
    @PastOrPresent(message = "Last Training Date must from the the Past")
    private LocalDateTime lastTrainingDate ;
    @FutureOrPresent(message = "Next Training Date must be a from now on ")
    private LocalDateTime nextTrainingDate ;
    private LocalDate countdownDeadline ;
    private Boolean registred ;
    @Min(value = 1 , message = "Min is 1")
    @Max(value = 7 , message = "Max i 7")
    private Integer remainingDaysPerWeek ;
    private Integer daysAttended ;
    
    @Column(updatable = false)
    LocalDateTime createdAt;
    Double totalPaid;






}