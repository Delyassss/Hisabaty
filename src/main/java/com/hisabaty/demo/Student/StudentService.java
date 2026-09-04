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
import java.time.temporal.TemporalAdjusters;

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
        School school = schoolRepo.findById(schoolId).orElseThrow(() -> new RuntimeException("School not found !"));
        Optional<Student> newStudent = studentRepo.findByCin(student.getCin());
        if (newStudent.isPresent()) // isPresent is a method that checks if the optional is empty or not 
           throw (new IllegalArgumentException("Error: Student [Cin :  " + student.getCin() + " ] is already registered!"));

        // check the type of license
        if (!school.getLicenseAvailable().contains(student.getTypeOfLicense()))
            throw (new IllegalArgumentException("Error: School does not offer this type of license!"));

        newStudent.get().setName(student.getName());
        newStudent.get().setCin(student.getCin());
        newStudent.get().setSchool(school);
        newStudent.get().setPhone(student.getPhone());
        if (student.getAlreadyPassedCode())
            newStudent.get().setStatus(Status.PRACTICAL_TRAINING);
        else
            newStudent.get().setStatus(Status.THEORY_TRAINING);

        if (!school.getLicenseAvailable().contains(student.getTypeOfLicense()))
            throw (new RuntimeException("Error: School does not offer this type of license!"));
        else
            newStudent.get().setTypeOfLicense(student.getTypeOfLicense());
        newStudent.get().setEmail(student.getEmail());
        newStudent.get().setRemainingDaysPerWeek(school.getPracticeDaysPerWeek());
    return  studentRepo.save(newStudent.get());
    }

    public Page<Student> getStudentsBySchool(Long schoolId, Pageable pageable)
    {
        if (schoolId < 0)
            throw (new IllegalArgumentException("Invalid school id!"));
        // Spring Data JPA automatically writes the SELECT * FROM students WHERE school_id = ?
        return studentRepo.findBySchoolId(schoolId, pageable);
    }
    public Student_Response_DTO getStudentById(Long id, Pageable pageable) 
    {
        if (id < 0)
            throw (new IllegalArgumentException("Invalid student id!"));
        Student student = studentRepo.findById(id).orElseThrow(() -> new StudentNotFound());
        return convertToStudentResponseDTO(student);
    }

























    // Days Schedule (this will run every Monday at 00:00 be)
    // Run at 00:00:00, regardless of the date or month, as long as the day is a Monday.
    @Scheduled(cron = "0 0 0 * * MON")
    public void ResetDays()
    {
        List<Student> students = studentRepo.findAll();
        if (students.isEmpty())
            throw (new StudentNotFound());
        students.forEach(one -> one.setRemainingDaysPerWeek(one.getSchool().getPracticeDaysPerWeek()));
        students.forEach(one -> one.setAttendanceStatus(AttendanceStatus.ABSENT));
        studentRepo.saveAll(students);
        System.out.println("Reset days success.");
    }



    
    @Scheduled  (cron = "0 0 0 * * *")
    public void setCountdownDeadline(){
        List<Student> students = studentRepo.findAll();

        if (students.isEmpty())
            throw (new StudentNotFound());

        students.forEach(one -> 
        {
            if (one.getCountdownDeadline().isBefore(LocalDate.now()))
            {
                one.setStatus(Status.THEORY_TRAINING_COMPLETED);
                one.setRemainingDaysPerWeek(0);
            }
            one.setAttendanceStatus(AttendanceStatus.ABSENT); // set attendance status to absent every day 
        });
        studentRepo.saveAll(students);
    }
    

    public Boolean AttendingCheck(Long studentId, Boolean Attended)
    {
        Student student = studentRepo.findById(studentId).orElseThrow(() -> new StudentNotFound());

        if (student.getRemainingDaysPerWeek() <= 0)
            throw (new StudentAttendaceLimitException(studentId));

        if (student.getStatus() != Status.THEORY_TRAINING && student.getStatus() != Status.PRACTICAL_TRAINING)
            throw (new RuntimeException("Student is not in training"));         
        if (Attended == true)
        {
            student.setRemainingDaysPerWeek(student.getRemainingDaysPerWeek() - 1); // for each day they attend we subtract one from the remaining days
            student.setAttendanceStatus(AttendanceStatus.PRESENT); // set attendance status to present and i need to set back after couple of hours 
            student.setDaysAttended(student.getDaysAttended() + 1); // increase the number of days they attended (ALL TIME)
            student.setLastTrainingDate(LocalDate.now());
            System.out.println("Attending check success.");  
        }
        else
        {
            student.setAttendanceStatus(AttendanceStatus.ABSENT);
            student.setNextTrainingDate(LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)));
            System.out.println("Attendance marked as absent.");
        }
        studentRepo.save(student);
        return Attended;
    }



    // Convert Utils
    public Student_Request_DTO convertToStudentRequestDTO(Student student)
    {
        Student_Request_DTO std = new Student_Request_DTO();
        std.setName(student.getName());
        std.setCin(student.getCin());
        std.setEmail(student.getEmail());
        std.setPhone(student.getPhone());
        std.setAdvancePayment(student.getAdvancePayment());
        std.setRemainingPayment(student.getRemainingPayment());
        std.setTypeOfLicense(student.getTypeOfLicense());
        std.setAlreadyPassedCode(student.getAlreadyPassedCode());
        std.setSchoolId(student.getSchool().getId());
        return std;
    }
    public Page<Student_Request_DTO> ToStudentRequestDTO(Page<Student> student)
    {
        Page<Student_Request_DTO> std ;
        std = student.map(s -> convertToStudentRequestDTO(s));
        return std;
    }



    public Student_Response_DTO convertToStudentResponseDTO(Student student)
    {
        Student_Response_DTO std = new Student_Response_DTO();
        std.setName(student.getName());
        std.setCin(student.getCin());
        std.setEmail(student.getEmail());
        std.setPhone(student.getPhone());
        std.setAdvancePayment(student.getAdvancePayment());
        std.setRemainingPayment(student.getRemainingPayment());
        std.setTypeOfLicense(student.getTypeOfLicense());
        std.setAlreadyPassedCode(student.getAlreadyPassedCode());
        std.setSchoolId(student.getSchool().getId());
        return std;
    }
    public Page<Student_Response_DTO> ToStudentResponseDTO(Page<Student> student)
    {
        Page<Student_Response_DTO> std ;
        std = student.map(s -> convertToStudentResponseDTO(s));
        return std;
    }

}