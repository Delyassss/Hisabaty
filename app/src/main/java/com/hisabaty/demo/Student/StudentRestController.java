package com.hisabaty.demo.Student;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.catalina.connector.Response;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schools/{schoolId}/students")
@Validated 
public class StudentRestController
{
    private final StudentService studentService;


    /* ************************************************************************** */
    /*                              POST REQUESTS                                 */
    /* ************************************************************************** */

    @PostMapping
    public ResponseEntity<Student_Response_DTO> addStudent(@Valid @RequestBody Student_Request_DTO student,
                                              @Positive @PathVariable Long schoolId)
    {
        Student std = studentService.createStudent(student, schoolId);
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.convertToStudentResponseDTO(std));
    }

    // When they click "Yes", your frontend code silently sends the POST request to /api/students/{id}/consume-practice
    @PostMapping("/{studentId}/attendance")
    public ResponseEntity<String> recordAttendance(@Positive @PathVariable("studentId") Long id,
                                                   @RequestParam(required = false) Boolean Attended)
    {
        studentService.AttendingCheck(id, Attended);
        String msg = Attended ? "Attendance recorded as present!" : "Attendance recorded as absent!";
        return ResponseEntity.status(HttpStatus.OK).body(msg);
    }
    
    // GET ALL STUDENTS -- I changed it to POST for the filters 
    @PostMapping("/filter")
    public ResponseEntity<Page<Student_Response_DTO>> getStudentsByfilter(@Valid @RequestBody Student_Request_DTO request, @Positive @PathVariable Long schoolId, Pageable pg)
    {
        Page<Student_Response_DTO>  stds = studentService.getStudentDynamically(request, schoolId, pg);
            
        return ResponseEntity.ok(stds);

    }

    /* ************************************************************************** */
    /*                              GET REQUESTS                                 */
    /* ************************************************************************** */

    @GetMapping
    public ResponseEntity<Page<Student_Response_DTO>> getStudentsBySchool( @Positive @PathVariable Long schoolId, Pageable pg)
    {
       Page<Student_Response_DTO> students = studentService.getStudentsBySchool(schoolId, pg);
     
        return ResponseEntity.ok(students);
    }


    @GetMapping("/{studentId}")
    public ResponseEntity<Student_Response_DTO> getStudentbyId(@Positive @PathVariable("studentId") Long id, Pageable pg)
    {
        Student_Response_DTO std = studentService.getStudentById(id, pg);
        return ResponseEntity.ok(std);
    }
    


    /* ************************************************************************* */
    /*                              PUT REQUESTS                                 */
    /* ************************************************************************** */


    @PutMapping("/update/{student_id}")
    ResponseEntity<Student_Response_DTO> UpdateStudent(@PathVariable("student_id") @Positive Long id, @Valid @RequestBody Student_Request_DTO request)
    {
        Student std = studentService.UpdateStudent(id , request);

        return ResponseEntity.ok(studentService.convertToStudentResponseDTO(std));
    }


    /* ************************************************************************** */
    /*                              DELETE REQUESTS                                 */
    /* ************************************************************************** */
    
    @DeleteMapping("/delete/{studentId}")
    ResponseEntity<Map<String, String>> DeleteStudent(@PathVariable @Positive Long studentId)
    {
        
        Boolean succes = studentService.DeleteStudent(studentId);
        if (!succes)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Student not found"));
        
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Student deleted successfully"));
    }

    /* ************************************************************************** */
    /*                              PATCH REQUESTS                                 */
    /* ************************************************************************** */
    
    @PatchMapping("/{studentId}")
    ResponseEntity<Student_Response_DTO> PatchStudent(@PathVariable @Positive Long studentId, @Valid @RequestBody Student_Patch_Request_DTO request)
    {
        Student std = studentService.PatchStudent(studentId, request);
        return ResponseEntity.ok(studentService.convertToStudentResponseDTO(std));
    }























































    // UTILS
    public Pageable getpages(int pageNumber, int pageSize)
    {
        if (pageNumber < 0 ||  pageSize < 0)
            throw new IllegalArgumentException("Invalid page number or page size value!");
        return  PageRequest.of(pageNumber, pageSize);
    }
}