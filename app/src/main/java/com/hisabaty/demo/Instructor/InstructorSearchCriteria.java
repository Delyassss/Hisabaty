package com.hisabaty.demo.Instructor;

import lombok.Data;
import java.time.LocalDate;

@Data
public class InstructorSearchCriteria {
    private Long id;
    private Long schoolId;
    private String name;
    private String cin;
    private String phone;
    private String email;
    private String capNumber;
    private String typeOfLicense;
    private InstructorSpeciality speciality;
    private InstructorStatus status;
    private Double salary;
    private Double hourlyRate;
    private LocalDate hireDate;
}
