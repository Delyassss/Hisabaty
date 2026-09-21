package com.hisabaty.demo.School;

import java.util.List;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class School_Request_DTO
{

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Phone is required")
    private String phone;

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "City is required")
    private String city;

    @NotNull(message = "State is required")
    private Boolean state;

    @NotNull(message = "Practice days is required")
    @Min(value = 1, message = "Practice days must be at least 1")
    @Max(value = 7, message = "Practice days must be at most 7")
    private Integer practiceDaysPerWeek;


    private List<String> licenseAvailable;

    private Long studentCount;
    private String studentCinPrefix;

    
}
