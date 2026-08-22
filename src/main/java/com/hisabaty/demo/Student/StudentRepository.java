package com.hisabaty.demo.Student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface StudentRepository extends JpaRepository<Student, Long>
{
    // Spring Boot automatically builds the SQL query for this!
    Page<Student> findBySchoolId(Long schoolId, Pageable pageable);
    Optional<Student> findByCin(String cin);
}