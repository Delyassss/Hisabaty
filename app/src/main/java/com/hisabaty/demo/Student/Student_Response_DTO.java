package com.hisabaty.demo.Student;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class Student_Response_DTO
{
    private Long id;
    private Long schoolId;

    private String name;
    private String cin;
    private String phone;
    private String email;
    private String typeOfLicense;
    private Boolean alreadyPassedCode;
    private Double advancePayment;
    private Double remainingPayment;
    private Double totalPaid;
    private Integer remainingDaysPerWeek;
    private Integer daysAttended;
    private LocalDateTime createdAt;
    private LocalDateTime lastTrainingDate;
    private LocalDateTime nextTrainingDate;
    private LocalDate examDate;
    private LocalDate countdownDeadline;
    private AttendanceStatus attendanceStatus;
    private Status status;
}