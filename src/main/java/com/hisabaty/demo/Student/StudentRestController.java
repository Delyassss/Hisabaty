package com.hisabaty.demo.Student;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/student")
public class StudentRestController
{
    private final StudentService studentService;


    // CREATE
    @PostMapping    
    public ResponseEntity<Student> addStudent(@Valid @RequestBody StudentDTO student,
                                              @RequestParam Long schoolID)
    {
        Student std = studentService.createStudent(student, schoolID);
        return ResponseEntity.status(HttpStatus.CREATED).body(std);
    }
    // GET BY SCHOOL ID
    @GetMapping
    public ResponseEntity<Page<StudentDTO>> getStudentsBySchool(@RequestParam Long schoolId,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size)
    {
        if (schoolId < 0)
            throw (new IllegalArgumentException("Invalid school id!"));

       Page<Student> students = studentService.getStudentsBySchool(schoolId, getpages(page, size));
        return ResponseEntity.ok(studentService.ToDTO(students));
    }

    // GET ALL STUDENTS
    @GetMapping
    public ResponseEntity<Page<StudentDTO>> getStudents(@RequestParam(required = false) String email,
                                                        @RequestParam(required = false) String phone,
                                                        @RequestParam(required = false) String cin,
                                                        @RequestParam(required = false) Status status,
                                                        @RequestParam(required = false) LocalDate examDate,
                                                        @RequestParam(required = false) AttendanceStatus attendanceStatus,
                                                        @RequestParam(required = false) LocalDate countdownDeadline,
                                                        @RequestParam(required = false) LocalDate lastTrainingDate,
                                                        @RequestParam(required = false) LocalDate nextTrainingDate,
                                                        @RequestParam(required = false) Boolean registred,                                                     
                                                        @RequestParam Long schoolId,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue =  "10") int size)
    {
        if (schoolId < 0)
            throw (new IllegalArgumentException("Invalid school id!"));


    }

    // When they click "Yes", your frontend code silently sends the POST request to /api/students/{id}/consume-practice
    @PostMapping("/{studentId}/attendance")
    public ResponseEntity<String> postAttendance(@PathVariable Long studentId)
    {
        if (studentId < 0)
            throw new IllegalArgumentException("Invalid student id!"); 
        studentService.AttendingCheck(studentId);
        return ResponseEntity.status(HttpStatus.OK).body("Attendance recorded successfully.");
    }















    // UTILS
    public Pageable getpages(int pageNumber, int pageSize)
    {
        if (pageNumber < 0 ||  pageSize < 0)
            throw new IllegalArgumentException("Invalid page number or page size value!");
        return  PageRequest.of(pageNumber, pageSize);
    }
}