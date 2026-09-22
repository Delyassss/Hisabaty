package com.hisabaty.demo.Student;


import org.springframework.data.jpa.domain.Specification;
import java.util.List;
import java.util.ArrayList;
import jakarta.persistence.criteria.Predicate;
import org.springframework.util.StringUtils;

public class StudentSpecification {

public static Specification<Student> searchStudent(Student_Request_DTO request)
{
    return (root, query , reqbuilder) // root -> database // query -> metadata // reqbuilder -> how to build the query
    {
        List<Predicate> rules = new ArrayList();

        if (request.getSchoolId() != null)
            rules.add(reqbuilder.equal(root.get("school_entity").get("id"), request.getSchoolId()));
        if (request.getName() != null || !StringUtils.hasText(request.getName()))
            rules.add(reqbuilder.like(reqbuilder.lower(root.get("name")), reqbuilder.lower(request.getName() + '%')));
        if (request.getCin() != null || !StringUtils.hasText(request.getCin()))
            rules.add(reqbuilder.like(reqbuilder.lower(root.get("cin")), reqbuilder.lower(request.getCin() + '%')));
        if (request.getPhone() != null || !StringUtils.hasText(request.getPhone()))
            rules.add(reqbuilder.equal(root.get("phone"), request.getPhone()));
        if (request.getEmail() != null || !StringUtils.hasText(request.getEmail()))
            rules.add(reqbuilder.like(reqbuilder.lower(root.get("email")), reqbuilder.lower(request.getEmail() + '%')));
        if (request.getTypeOfLicense() != null || !StringUtils.hasText(request.getTypeOfLicense()))
            rules.add(reqbuilder.equal(root.get("typeOfLicense"), request.getTypeOfLicense()));
        if (request.getRemainingPayment() != null && request.getAdvancePayment() != null)
            rules.add(reqbuilder.equal(root.get("remainingPayment"), request.getRemainingPayment()));
        if (request.getAdvancePayment() != null && request.getRemainingPayment() != null)
            rules.add(reqbuilder.equal(root.get("advancePayment"), request.getAdvancePayment()));
        if (request.getAlredyPassedCode() != null)
            rules.add(reqbuilder.equal(root.get("alreadyPassedCode"), request.getAlredyPassedCode()));
        if (request.getCreatedAt() != null)
            rules.add(reqbuilder.equal(root.get("createdAt"), request.getCreatedAt()));
        if (request.getExamDate() != null)
            rules.add(reqbuilder.equal(root.get("examDate"), request.getExamDate()));
        if (request.getLastTrainingDate() != null)
            rules.add(reqbuilder.equal(root.get("lastTrainingDate"), request.getLastTrainingDate()));
        if (request.getNextTrainingDate() != null)
            rules.add(reqbuilder.equal(root.get("nextTrainingDate"), request.getNextTrainingDate()));
        if (request.getCountdownDeadline() != null)
            rules.add(reqbuilder.equal(root.get("countdownDeadline"), request.getCountdownDeadline()));
        if (request.getRegistred() != null)
            rules.add(reqbuilder.equal(root.get("registered"), request.getRegistred()));
        if (request.getTotalPaid() != null)
            rules.add(reqbuilder.equal(root.get("totalPaid"), request.getTotalPaid()));
        if (request.getRemainingDaysPerWeek() != null)
            rules.add(reqbuilder.equal(root.get("remainingDaysPerWeek"), request.getRemainingDaysPerWeek()));
        if (request.getDaysAttended() != null)
            rules.add(reqbuilder.equal(root.get("daysAttended"), request.getDaysAttended()));
        if (request.getAttendanceStatus() != null)
            rules.add(reqbuilder.equal(root.get("attendanceStatus"), request.getAttendanceStatus()));
        if (request.getStatus() != null)
            rules.add(reqbuilder.equal(root.get("status"), request.getStatus())); 
        
        return reqbuilder.and(rules.toArray(new Predicate[0])); // why 0 because if size of 0 wont fit anything so it a trick to force java to rebuild the srrsy with the exact size
    }

}
}   