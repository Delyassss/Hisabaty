package com.hisabaty.demo.Student;

import ch.qos.logback.core.joran.conditional.ElseAction;
import com.hisabaty.demo.School.School;
import com.hisabaty.demo.School.SchoolRepository;
import com.hisabaty.demo.Student.StudentDTO;
import com.hisabaty.demo.Student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class StudentService
{
    private final StudentRepository studentRepo;
    private final SchoolRepository schoolRepo;

    // CREATE
    public Student createStudent(StudentDTO student, Long schoolId)
    {
        if (student == null || schoolId < 0)
            throw (new IllegalArgumentException("Invalid student or school id!"));
        School school = schoolRepo.findById(schoolId).orElseThrow(() -> new RuntimeException("school not found"));
        Optional<Student> newStudent = studentRepo.findByCin(student.getCin());
        if (newStudent.isPresent())
           throw (new IllegalArgumentException("Error: Student [Cin :  " + student.getCin() + " ] is already registered!"));
        newStudent.get().setName(student.getName());
        newStudent.get().setCin(student.getCin());
        newStudent.get().setSchool(school);
        newStudent.get().setPhone(student.getPhone());
        if (student.getAlreadyPassedCode())
            newStudent.get().setStatus(Status.PRACTICAL_TRAINING);
        else
            newStudent.get().setStatus(Status.THEORY_TRAINING);

        newStudent.get().setEmail(student.getEmail());
        newStudent.get().setRemainingDays(school.getPracticeDays());
    return  studentRepo.save(newStudent.get());
    }

    public Page<Student> getStudentsBySchool(Long schoolId, Pageable pageable)
    {
        if (schoolId < 0)
            throw (new IllegalArgumentException("Invalid school id!"));
        // Spring Data JPA automatically writes the SELECT * FROM students WHERE school_id = ?
        return studentRepo.findBySchoolId(schoolId, pageable);
    }

























    // Days Schedule
    @Scheduled(cron = "0 0 0 * * MON")
    public void ResetDays()
    {
        List<Student> students = studentRepo.findAll();
        if (students.isEmpty())
            throw (new StudentNotFound());
        students.forEach(one -> one.setRemainingDays(one.getSchool().getPracticeDays()));
        studentRepo.saveAll(students);
        System.out.println("Reset days success.");
    }
    public void AttendingCheck()
    {
        List<Student> students = studentRepo.findAll();
        if (students.isEmpty())
            throw (new StudentNotFound());
        students.forEach(one -> one.setRemainingDays(one.getRemainingDays() - 1));
        studentRepo.saveAll(students);
        System.out.println("Attending check success.");
    }



    // Convert Utils
    public StudentDTO convertToDTO(Student student)
    {
        StudentDTO std = new StudentDTO();
        std.setName(student.getName());
        std.setCin(student.getCin());
        std.setEmail(student.getEmail());
        std.setPhone(student.getPhone());
        return std;
    }
    public Page<StudentDTO> ToDTO(Page<Student> student)
    {
        Page<StudentDTO> std ;
        std = student.map(s -> convertToDTO(s));
        return std;
    }
}