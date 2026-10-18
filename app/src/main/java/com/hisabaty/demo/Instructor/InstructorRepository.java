package com.hisabaty.demo.Instructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long>, JpaSpecificationExecutor<Instructor> {

    Optional<Page<Instructor>> findBySchoolId(Long schoolId, Pageable pageable);

    Optional<Instructor> findByCin(String cin);

    @Override
    Page<Instructor> findAll(Specification<Instructor> spec, Pageable pageable);

    boolean existsByCinIgnoreCase(String cin);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByPhone(String phone);
}
