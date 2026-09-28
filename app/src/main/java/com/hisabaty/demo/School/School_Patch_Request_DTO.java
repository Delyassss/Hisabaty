package com.hisabaty.demo.School;

import java.util.List;

import com.hisabaty.demo.Student.Student;

import jakarta.validation.constraints.Min;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter

public class School_Patch_Request_DTO
{
  
    private String name;

    private String address;

    private String phone;

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email address")
    private String email;

    private String city;

    private Boolean state;

    private String province;

    private List<Student> students;


    @Min(value = 1, message = "Practice days must be at least 1")
    @Max(value = 7, message = "Practice days must be at most 7")
    private Integer practiceDaysPerWeek;


    private List<String> licenseAvailable;

    private Integer studentCount;
    private String studentCinPrefix;

}