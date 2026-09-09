package com.hisabaty.demo.Student;

import lombok.Data;
import java.time.LocalDate;

@Data
public class StudentSearchCriteria
{
    private String email;
    private Long schoolId;
    private Long id;
    private String name;
    private String phone;
    private String cin;
    private Status status;
    private AttendanceStatus attendanceStatus;
    private LocalDate createdAt;
    private LocalDate examDate;
    private LocalDate lastTrainingDate;
    private LocalDate nextTrainingDate;
    private LocalDate countdownDeadline;
    private Boolean registred;
    private Double advancePayment;
    private Double remainingPayment;
    private Double totalPaid;
    private Integer remainingDaysPerWeek;
    private Integer daysAttended;
    private String typeOfLicense;
    private Boolean alreadyPassedCode;
    
}