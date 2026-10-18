package com.hisabaty.demo.Instructor;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class Instructor_Response_DTO {

    private Long id;
    private Long schoolId;
    private String schoolName;

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
    private LocalDateTime createdAt;

    public static Instructor_Response_DTO toInstructorResponseDTO(Instructor instructor) {
        if (instructor == null) {
            return null;
        }
        Instructor_Response_DTO dto = new Instructor_Response_DTO();
        dto.setId(instructor.getId());
        if (instructor.getSchool() != null) {
            dto.setSchoolId(instructor.getSchool().getId());
            dto.setSchoolName(instructor.getSchool().getName());
        }
        dto.setName(instructor.getName());
        dto.setCin(instructor.getCin());
        dto.setPhone(instructor.getPhone());
        dto.setEmail(instructor.getEmail());
        dto.setCapNumber(instructor.getCapNumber());
        dto.setTypeOfLicense(instructor.getTypeOfLicense());
        dto.setSpeciality(instructor.getSpeciality());
        dto.setStatus(instructor.getStatus());
        dto.setSalary(instructor.getSalary());
        dto.setHourlyRate(instructor.getHourlyRate());
        dto.setHireDate(instructor.getHireDate());
        dto.setCreatedAt(instructor.getCreatedAt());
        return dto;
    }

    public static Page<Instructor_Response_DTO> toInstructorResponseDTO_All(Page<Instructor> instructors) {
        if (instructors == null) {
            return null;
        }
        return instructors.map(Instructor_Response_DTO::toInstructorResponseDTO);
    }
}
