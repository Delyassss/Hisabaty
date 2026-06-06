package com.hisabaty.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.hisabaty.demo.Student;



@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    // Spring Boot automatically builds the SQL query for this!
    List<Student> findBySchoolId(Long schoolId);
}