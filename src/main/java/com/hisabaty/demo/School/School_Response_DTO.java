package com.hisabaty.demo.School;

import java.util.List;
import java.util.ArrayList;

import org.springframework.data.domain.Page;

import lombok.Data;





@Data
public class School_Response_DTO
{
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String city;
    private Boolean state;
    private Integer practiceDaysPerWeek;
    private List<String> licenseAvailable;
    private Integer studentCount;
    private String studentCinPrefix;



    public static School_Response_DTO ToSchoolResponseDTO   (School sc)
    {
        School_Response_DTO responseDTO = new School_Response_DTO();
        responseDTO.setId(sc.getId());
        responseDTO.setName(sc.getName());
        responseDTO.setAddress(sc.getAddress());
        responseDTO.setPhone(sc.getPhone());
        responseDTO.setEmail(sc.getEmail());
        responseDTO.setCity(sc.getCity());
        responseDTO.setState(sc.getState());
        responseDTO.setPracticeDaysPerWeek(sc.getPracticeDaysPerWeek());
        responseDTO.setLicenseAvailable(sc.getLicenseAvailable());
        responseDTO.setStudentCount(sc.getStudents().size());
        responseDTO.setStudentCinPrefix(sc.getStudentCinPrefix());
        return responseDTO;
    }


    public static Page<School_Response_DTO> ToSchoolResponseDTO_All(Page<School> schools)
    {
        Page<School_Response_DTO> sch  = schools.map(School_Response_DTO::ToSchoolResponseDTO);
        return sch;
    }
}
