package com.hisabaty.demo.School;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import com.hisabaty.demo.Student.*;

@Service
@RequiredArgsConstructor // this is an annotation to inject the dependencies into the constructor  so no need for @Autowired
public class SchoolService {

    private final SchoolRepository schoolRepository;
    private final StudentRepository studentRepo;
    private StudentService studentService;


    /* ************************************************************************** */
    /*                              GET REQUESTS                                  */
    /* ************************************************************************** */

    Page<Student_Response_DTO> getStudent(Pageable pg) {
        return StudentService.ToStudentResponseDTO(studentRepo.findAll(pg));
    }

    Student_Response_DTO getStudentById(Long studentId, Long schoolId) {
        Optional<Student> std = studentRepo.findById(studentId);
        if (std == null || std.isEmpty()) {
            return null;
        }
        if (std.get().getSchool().getId() != schoolId) {
            return null;
        }

        return StudentService.convertToStudentResponseDTO(std.get());
    }

    Page<Student_Response_DTO> getStudentsByFilter(Student_Request_DTO request, Long schoolId, Pageable pg) {

        Specification<Student> spec = StudentSpecification.searchStudent(request, schoolId);

        return studentRepo.findAll(spec, pg).map(StudentService::convertToStudentResponseDTO);
    }

    public Page<School_Response_DTO> getAllSchools(Pageable pg) {
        Page<School_Response_DTO> sch = schoolRepository.findAllSchools(pg).map(School_Response_DTO::ToSchoolResponseDTO);
        return sch;
    }

    public School_Response_DTO getSchoolById(Long school_id, Pageable pg) {
        Optional<School> sch = schoolRepository.findById(school_id);
        if (sch == null || sch.isEmpty()) {
            return null;
        }
        return School_Response_DTO.ToSchoolResponseDTO(sch.get());
    }





    /* ************************************************************************** */
    /*                              POST REQUESTS                                 */
    /* ************************************************************************** */


    public School addSchool(School_Request_DTO request)
    {
        Optional<School> sch = schoolRepository.getSchoolByName(request.getName()); //  we need another method to check if the school with the same name exist in the STATE

        if (sch != null && !sch.isEmpty())
            return sch.get();

        sch = settersSchool(request, sch);
        schoolRepository.save(sch.get());
        return sch.get();
    }

    public Student_Response_DTO addStudent(Student_Request_DTO request, Long schoolId)
    {
        Student std = studentService.createStudent(request, schoolId);
        if (std == null)
            return null;
        return StudentService.convertToStudentResponseDTO(std);
    }


    /* ************************************************************************** */
    /*                              PUT REQUESTS                                 */
    /* ************************************************************************** */


    public School UpdateSchool(Long schoolId, School_Request_DTO request)
    {
        Optional<School> sch = schoolRepository.findById(schoolId);

        if (request == null)
                return  sch.get();

        sch = settersSchool(request, sch);

        return schoolRepository.save(sch.get());
    }

    public Student_Response_DTO UpdateStudent(Long schoolId, Long student_id , Student_Request_DTO request)
    {
        Optional<Student> std = studentRepo.findById(student_id);
        if (std == null || std.isEmpty())
        {
           return addStudent(request, schoolId);
        }



    }






























    // UTILS
    public Optional<School> settersSchool(School_Request_DTO request , Optional<School> sch)
    {
        sch.get().setName(request.getName());
        sch.get().setAddress(request.getAddress());
        sch.get().setPhone(request.getPhone());
        sch.get().setEmail(request.getEmail());
        sch.get().setLicenseAvailable(request.getLicenseAvailable());
        sch.get().setPracticeDaysPerWeek(request.getPracticeDaysPerWeek());
        sch.get().setState(request.getState());
        sch.get().setCity(request.getCity());
        sch.get().setStudents(request.getStudents());
        sch.get().setStudentCount(request.getStudentCount());
        return sch;
    }

}

