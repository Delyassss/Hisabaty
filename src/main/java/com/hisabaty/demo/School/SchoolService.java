package com.hisabaty.demo.School;


import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.hisabaty.demo.Student.*;



@Service
@RequiredArgsConstructor // this is an annotation to inject the dependencies into the constructor  so no need for @Autowired
public class SchoolService
{
    private final SchoolRepository schoolRepository;
    private final StudentRepository studentRepo;


    Page<Student_Response_DTO> getStudent(Pageable pg)
    {
        return StudentService.ToStudentResponseDTO(studentRepo.findAll(pg));
    }


    Student_Response_DTO getStudentById(Long studentId, Long schoolId)
    {
        Optional<Student> std = studentRepo.findById(studentId);
        if (std == null  || std.isEmpty())
            return null;
        if (std.get().getSchool().getId() != schoolId)
            return null;

        return StudentService.convertToStudentResponseDTO(std.get());
    }

    Page<Student_Response_DTO> getStudentsByFilter(Student_Request_DTO request,Long id, Pageable pg)
    {
        request.setSchoolId(id); // set school id just to make sure the request in on the correct school
        Specification spec = StudentSpecification.searchStudent(request);
        return studentRepo.findAll(spec, pg).map(StudentService::convertToStudentResponseDTO);
    }

    public Page<School_Response_DTO> getAllSchools(Pageable pg)
    {
        Page<School_Response_DTO> sch =  schoolRepository.findAllSchools(pg).map(School_Response_DTO::ToSchoolResponseDTO);
        return sch;
    }




}