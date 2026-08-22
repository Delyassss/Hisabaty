package com.hisabaty.demo.School;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long>
{
    // JpaRepository gives you built-in methods like .save(), .findAll(), and .findById()
}