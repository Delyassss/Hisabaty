package com.hisabaty.demo.Student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {

    // Spring Boot automatically builds the SQL query for this!
    Optional<Page<Student>> findBySchoolId(Long schoolId, Pageable pageable);

    Optional<Student> findByCin(String cin);

    // Added from JpaSpecificationExecutor (you don't need to write anything for this)
    @Override // we add it just to override the method according to our needs
    Page<Student> findAll(Specification<Student> spec, Pageable pageable);
}
