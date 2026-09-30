package com.hisabaty.demo.Student;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * Student_Patch_Request_DTO
 */

@Data 
public class Student_Patch_Request_DTO extends Student_Request_DTO
{
    @Min(value = 1 , message = "ID must be positive")
    private  Long schoolId;
    
    private  String name;

    @Pattern(regexp = "^[A-Z]{1,2}[0-9]{4,6}$", message = "Invalid Moroccan CIN format")
    private String cin;

    @Pattern(regexp = "^[0-9]{8,15}$", message = "Invalid phone number format")
    private String phone;

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format")
    private String email;

    private String typeOfLicense;

    private Boolean alreadyPassedCode;
    private Double advancePayment;
    private Double remainingPayment;

    private Status status ;

    private AttendanceStatus attendanceStatus ;

    
    @FutureOrPresent(message = "Exam date must be a from now on ")
    private LocalDate examDate ;
    @FutureOrPresent(message = "Exam date must be a from now on ")
    private LocalDateTime lastTrainingDate ;
    @FutureOrPresent(message = "Next Training Date must be a from now on ")
    private LocalDateTime nextTrainingDate ;
    private LocalDate countdownDeadline ;
    private Boolean registred ;
    @Min(value = 1 , message = "Min is 1")
    @Max(value = 7 , message = "Max i 7")
    private Integer remainingDaysPerWeek ;
    private Integer daysAttended ;

}
