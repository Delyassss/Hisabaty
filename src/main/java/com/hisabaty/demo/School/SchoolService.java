import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import com.hisabaty.demo.School.SchoolRepository;
import com.hisabaty.demo.Student.*;



@Service
@RequiredArgsConstructor // this is an annotation to inject the dependencies into the constructor  so no need for @Autowired
public class SchoolService
{
    private final SchoolRepository schoolRepository;
    private final StudentRepository studentRepo;


    Page<Student_Response_DTO>  getStudent(Pageable pg)
    {
        return StudentService.ToStudentResponseDTO(studentRepo.findAll(pg));
    }


    Student_Response_DTO getStudentById(Long id)
    {
        Optional<Student> std = studentRepo.findById(id);
        if (std == null  || std.isEmpty())
            return null;
        if (std.get().getSchool().getId() != getSchoolId())
            return null;

        return studentRepo.findById(id).map(this::convertToStudentResponseDTO);
    }

    Page<Student_Response_DTO> getStudentsByFilter(Student_Request_DTO request, Pageable pg)
    {
        Long id = getSchoolId();
        request.setSchoolId(id);
        Specification spec = StudentSpecification.searchStudent(request);
        return schoolRepository.findAll(spec, pg).map(this::convertToStudentResponseDTO);
    }

    public Page<School_Response_DTO> getAllSchools(Pageable pg)
    {
        return schoolRepository.FindAllSchools(pg).map(this::ToSchoolResponseDTO);
    }




}