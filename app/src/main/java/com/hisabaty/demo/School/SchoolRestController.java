package com.hisabaty.demo.School;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.hisabaty.demo.School.*;
import com.hisabaty.demo.Student.*;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/schools")
@RequiredArgsConstructor
@Validated
public class SchoolRestController {

    @Autowired
    private final SchoolService schoolService;
    @Autowired
    private StudentService studentService;

    /* ************************************************************************** */
    /*                              GET REQUESTS                                  */
    /* ************************************************************************** */

    // @GetMapping("/students")
    // ResponseEntity<Page<Student_Response_DTO>> getStudents(Pageable pg) {

    //     Page<Student_Response_DTO> stds = schoolService.getStudent(pg);

    //     if (stds == null || stds.isEmpty()) {
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    //     }
    //     return ResponseEntity.ok(stds);
    // }

    // @GetMapping("/{school_id}/students/{student_id}")
    // ResponseEntity<Student_Response_DTO> getStudentById(@PathVariable Long student_id, @PathVariable Long school_id) {
    //     Student_Response_DTO std = schoolService.getStudentById(student_id, school_id);
    //     if (std == null) {
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    //     }
    //     return ResponseEntity.ok(std);
    // }

    // @GetMapping("{school_id}/students/filter")
    // public ResponseEntity<Page<Student_Response_DTO>> getStudentsByFilter(Student_Request_DTO request, @PathVariable Long school_id, Pageable pg)
    // {
    //     Page<Student_Response_DTO> stds = schoolService.getStudentsByFilter(request, school_id, pg);
    //     if (stds == null || stds.isEmpty()) {
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    //     }

    //     return ResponseEntity.ok(stds);
    // }

    @GetMapping("/admin/allSchools")
    public ResponseEntity<Page<School_Response_DTO>> getAllSchools(Pageable pg)
    {
        Page<School_Response_DTO> schools = schoolService.getAllSchools(pg); // Page does not return null just Empty

        // Always return 200 OK.
        // If empty, it automatically returns a valid Page JSON with an empty "content" array.
        return ResponseEntity.ok(schools);
    }

    // DEFAULT ENDPOINT
    @GetMapping("{school_id}")
    public ResponseEntity<School_Response_DTO> getSchoolById(@PathVariable @Positive Long school_id)
    {
        School_Response_DTO  sch = schoolService.getSchoolById(school_id); // NotFoundExeption is thrown here if school not found
        return ResponseEntity.ok(sch);
    }
    
    /* ************************************************************************** */
    /*                              POST REQUESTS                                 */
    /* ************************************************************************** */

        @PostMapping
        public ResponseEntity<School_Response_DTO> createSchool(@Valid  @RequestBody School_Request_DTO request)
        {
            School sch = schoolService.addSchool(request);
            return ResponseEntity.ok(School_Response_DTO.ToSchoolResponseDTO(sch));
        }
    // @PostMapping("/create/{school_id}/student")
    // public ResponseEntity<Student_Response_DTO> addStudent(@RequestBody Student_Request_DTO request , @PathVariable Long school_id)
    // {
    //     Student_Response_DTO std = schoolService.addStudent(request, school_id);
    //     if (std == null)
    //         return ResponseEntity.notFound().build();
    //     return ResponseEntity.ok(std);
    // }

    /* ************************************************************************** */
    /*                              PUT REQUESTS                                 */
    /* ************************************************************************** */


    @PutMapping("/{school_id}")
    public ResponseEntity<School_Response_DTO>  updateSchool(@PathVariable @Positive Long school_id, @Valid @RequestBody School_Request_DTO request)
    {
        School sch = schoolService.UpdateSchool(school_id, request);
        return ResponseEntity.ok(School_Response_DTO.ToSchoolResponseDTO(sch));
    }

    // @PutMapping("/update/{school_id}/{student_id}")
    // public ResponseEntity<Student> Update_Student(@PathVariable Long school_id, @PathVariable Long student_id , @RequestBody Student_Request_DTO request)
    // {
    //     Student std   = schoolService.UpdateStudent(school_id, student_id, request);

    //     if (std == null)
    //         return ResponseEntity.notFound().build();
    //     return ResponseEntity.ok(std);
    // }

    /* ************************************************************************** */
    /*                              DELETE REQUESTS                               */
    /* ************************************************************************** */


    @DeleteMapping("/{school_id}")
    public ResponseEntity<Void> deleteSchool(@PathVariable @Positive Long school_id)
    {
        Boolean succes = schoolService.DeleteSchool(school_id);
        if (!succes)
                return ResponseEntity.notFound().build();

        return ResponseEntity.noContent().build();
    }

    // @DeleteMapping("{school_id}/{student_id}")
    // public ResponseEntity<Void> deleteStudent(@PathVariable Long school_id, @PathVariable Long student_id)
    // {
    //     Boolean succes = schoolService.DeleteStudent(school_id, student_id);
    //     if (!succes)
    //             return ResponseEntity.notFound().build();
    //     return ResponseEntity.noContent().build();
    // }








}  

