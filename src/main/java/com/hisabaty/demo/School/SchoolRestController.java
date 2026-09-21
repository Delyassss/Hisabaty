import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hisabaty.demo.School.School;

import lombok.RequiredArgsConstructor;

import com.hisabaty.demo.Student.*;



@RestController
@RequestMapping("/v1/school")
@RequiredArgsConstructor
public class SchoolRestController
{

    private final SchoolService schoolService;


    @GetMapping("/students")
    ResponseEntity<Page<Student_Response_DTO>> getStudents(Pageable pg)
    {
        Page<Student_Response_DTO> stds = schoolService.getStudent(pg);

        if (stds == null || stds.isEmpty())
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(stds);
    }

    @GetMapping("/students/{id}")
    ResponseEntity<Student_Response_DTO> getStudentById(@PathVariable Long id)
    {
        Student_Response_DTO std = schoolService.getStudentById(id);
        if (std == null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(std);
    }


    @GetMapping("/students/filter")
    public ResponseEntity<Page<Student_Response_DTO>> getStudentsByFilter(Student_Request_DTO request , Pageable pg)
    {
        Page<Student_Response_DTO> stds = schoolService.getStudentsByFilter(request, pg);
        if (stds == null || stds.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        
        return ResponseEntity.ok(stds);
    }

    @GetMapping
    public ResponseEntity<Page<School_Response_DTO>> getAllSchools(Pageable pg)
    {
        Page<School_Response_DTO> schools = schoolService.getAllSchools(pg);
        if (schools == null || schools.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(schools);
    }

    
    

    
}   