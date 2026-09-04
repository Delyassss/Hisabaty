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
    public ResponseEntity<Student_Response_DTO> addStudent(@Valid @RequestBody StudentDTO student,
                                              @RequestParam Long schoolID)
    {
        Student std = studentService.createStudent(student, schoolID);
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.convertToStudentRequestDTO(std));
    }
    // GET BY SCHOOL ID
    @GetMapping("/school/{schoolId}")
    public ResponseEntity<Page<Student_Response_DTO>> getStudentsBySchool(@PathVariable Long schoolId,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size)
    {
        if (schoolId < 0)
            throw (new IllegalArgumentException("Invalid school id!"));

       Page<Student> students = studentService.getStudentsBySchool(schoolId, getpages(page, size));
        return ResponseEntity.ok(studentService.ToStudentResponseDTO(students));
    }


    @GetMapping("/{studentId}")
    public ResponseEntity<Student_Response_DTO> getStudentbyId(@PathVariable("studentId") Long id,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size)
    {
        if (id < 0)
            throw new IllegalArgumentException("Invalid student id!");
        Student_Response_DTO std = studentService.getStudentById(id, getpages(page, size));
        return ResponseEntity.ok(std);
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
public ResponseEntity<String> recordAttendance(@PathVariable("studentId") Long id,
                                               @RequestParam(required = false) Boolean Attended)
{
    if (id < 0)
        throw new IllegalArgumentException("Invalid student id!"); 

    studentService.AttendingCheck(id, Attended);
    String msg = Attended ? "Attendance recorded as present!" : "Attendance recorded as absent!";
    return ResponseEntity.status(HttpStatus.OK).body(msg);
}


























    // UTILS
    public Pageable getpages(int pageNumber, int pageSize)
    {
        if (pageNumber < 0 ||  pageSize < 0)
            throw new IllegalArgumentException("Invalid page number or page size value!");
        return  PageRequest.of(pageNumber, pageSize);
    }
}