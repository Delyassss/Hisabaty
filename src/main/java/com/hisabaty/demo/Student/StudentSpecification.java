import org.springframework.data.jpa.domain.Specification;

public class StudentSpecification {

public static Specification<Student> searchStudent(Student_Request_DTO request)
{
    return (root, query , reqbuilder)
    {
        
    }
}    
}
