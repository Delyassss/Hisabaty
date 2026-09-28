package com.hisabaty.demo.School;


public class School_Patch_Request_DTO
{
  
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Phone is required")
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