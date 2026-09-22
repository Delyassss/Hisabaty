package com.hisabaty.demo.School;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hisabaty.demo.School.*;
import com.hisabaty.demo.Student.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v1/school")
@RequiredArgsConstructor
public class SchoolRestController {

    private final SchoolService schoolService;

    @GetMapping("/students")
    ResponseEntity<Page<Student_Response_DTO>> getStudents(Pageable pg) {
        Page<Student_Response_DTO> stds = schoolService.getStudent(pg);

        if (stds == null || stds.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(stds);
    }

    @GetMapping("/{school_id}/students/{student_id}")
    ResponseEntity<Student_Response_DTO> getStudentById(@PathVariable Long student_id, @PathVariable Long school_id) {
        Student_Response_DTO std = schoolService.getStudentById(student_id, school_id);
        if (std == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(std);
    }

    @GetMapping("/students/{id}/filter")
    public ResponseEntity<Page<Student_Response_DTO>> getStudentsByFilter(Student_Request_DTO request, @PathVariable Long id, Pageable pg)
    {
        Page<Student_Response_DTO> stds = schoolService.getStudentsByFilter(request, id, pg);
        if (stds == null || stds.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(stds);
    }

    @GetMapping
    public ResponseEntity<Page<School_Response_DTO>> getAllSchools(Pageable pg) {
        Page<School_Response_DTO> schools = schoolService.getAllSchools(pg);
        if (schools == null || schools.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(schools);
    }

}
