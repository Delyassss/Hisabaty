import org.springframework.web.bind.annotation.*;
package com.hisabaty.demo.Student;





@RestController
@RequestMapping("/v1/school")
public class SchoolRestController
{

    private final SchoolService schoolService;


    @GetMapping("/students")
    ResponseEntity<Page<Student_Response_DTO>> getStudent(Pageable pg)
    {
        Page<Student_Response_DTO> stds = schoolService.getStudent(pg);

        if (stds == null || stds.isEmpty())
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No students found");
        }
        return ResponseEntity.ok(stds);
    }

    @GetMapping("/students/{id}")
    ResponseEntity<Student_Response_DTO> getStudentById(@PathVariable Long id)
    {
        Student_Response_DTO std = schoolService.getStudentById(id);
        if (std == null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No student found with id " + id);
        }
        return ResponseEntity.ok(std);
    }


    @GetMapping("/spec")
    public ResponseEntity<Page<Student_Response_DTO>> getStudentsByFilter(Student_Request_DTO request , Pageable pg)
    {
        Page<Student_Response_DTO> stds = schoolService.getStudentByfilter(request, pg);
        if (stds == null || stds.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No students found with the given filter");
        
        return ResponseEntity.ok(stds);
    }

    
    

    
}   