import org.springframework.data.jpa.domain.Specification;
import java.util.List;
import java.util.ArrayList;
import jakarta.persistence.criteria.Predicate;

public class StudentSpecification {

public static Specification<Student> searchStudent(Student_Request_DTO request)
{
    return (root, query , reqbuilder) // root -> database // query -> metadata // reqbuilder -> how to build the query
    {
        List<Predicate> rules = new ArrayList();

        if (request.getSchoolId() != null)
            rules.add(reqbuilder.equal(root.get("school_entity").get("id"), request.getSchoolId()));
        if (request.getName() != null)
            rules.add(reqbuilder.equal(root.get("name"), request.getName()));
        if (request.)
        




    }
}    
}
