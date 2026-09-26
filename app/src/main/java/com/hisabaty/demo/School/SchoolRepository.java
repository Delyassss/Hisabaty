package com.hisabaty.demo.School;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long>
{
    // JpaRepository gives you built-in methods like .save(), .findAll(), and .findById()
    //Optional<School> findByCin(String cin);

    @Query("SELECT DISTINCT s FROM School s LEFT JOIN FETCH s.students")
    Page<School> findAllSchools(Pageable pg);

    Optional<School> getSchoolByName(String name);

}