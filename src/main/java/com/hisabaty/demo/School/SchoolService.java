import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.hisabaty.demo.Student.Exceptions.StudentNotFound;
import com.hisabaty.demo.Student.*;



@Service
@RequiredArgsConstructor // this is an annotation to inject the dependencies into the constructor  so no need for @Autowired
public class SchoolService
{
    private final SchoolRepository schoolRepository;
    private final StudentRepository studentRepo;


    Page<Student_Response_DTO>  getStudent(Pageable pg)
    {
        return ToStudentResponseDTO(studentRepo.findAll(pg));
    }


    Student_Response_DTO getStudentById(Long id)
    {
        Student std = studentRepo.findById(id);
        if (std == null  || std.isEmpty())
            return null;
        if (std.getSchool().getId() != getSchoolId())
            return null;

        return schoolRepository.findByStudentId(id).map(this::convertToStudentResponseDTO);
    }

    Page<Student_Response_DTO> getStudentsByFilter(Student_Request_DTO request, Pageable pg)
    {
        Long id = getSchoolId();
        request.setSchoolId(id);
        Specification spec = StudentSpecification.searchStudent(request);
        return schoolRepository.findAll(spec, pg).map(this->convertToStudentResponseDTO);
    }

    public Page<School> getAllSchools(Pageable pg)
    {
        return schoolRepository.FindAllSchools(pg);
    }




}