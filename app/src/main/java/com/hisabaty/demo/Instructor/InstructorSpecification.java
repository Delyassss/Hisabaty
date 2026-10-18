package com.hisabaty.demo.Instructor;

import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class InstructorSpecification {

    public static Specification<Instructor> searchInstructor(Instructor_Request_DTO request, Long authenticatedSchoolId) {
        return (root, query, reqbuilder) -> {
            List<Predicate> rules = new ArrayList<>();

            if (authenticatedSchoolId != null) {
                rules.add(reqbuilder.equal(root.get("school").get("id"), authenticatedSchoolId));
            }

            if (request == null) {
                return reqbuilder.and(rules.toArray(new Predicate[0]));
            }

            if (StringUtils.hasText(request.getName())) {
                rules.add(reqbuilder.like(reqbuilder.lower(root.get("name")), request.getName().toLowerCase() + "%"));
            }
            if (StringUtils.hasText(request.getCin())) {
                rules.add(reqbuilder.like(reqbuilder.lower(root.get("cin")), request.getCin().toLowerCase() + "%"));
            }
            if (StringUtils.hasText(request.getPhone())) {
                rules.add(reqbuilder.equal(root.get("phone"), request.getPhone()));
            }
            if (StringUtils.hasText(request.getEmail())) {
                rules.add(reqbuilder.like(reqbuilder.lower(root.get("email")), request.getEmail().toLowerCase() + "%"));
            }
            if (StringUtils.hasText(request.getCapNumber())) {
                rules.add(reqbuilder.like(reqbuilder.lower(root.get("capNumber")), request.getCapNumber().toLowerCase() + "%"));
            }
            if (StringUtils.hasText(request.getTypeOfLicense())) {
                rules.add(reqbuilder.equal(root.get("typeOfLicense"), request.getTypeOfLicense()));
            }
            if (request.getSpeciality() != null) {
                rules.add(reqbuilder.equal(root.get("speciality"), request.getSpeciality()));
            }
            if (request.getStatus() != null) {
                rules.add(reqbuilder.equal(root.get("status"), request.getStatus()));
            }
            if (request.getSalary() != null) {
                rules.add(reqbuilder.greaterThanOrEqualTo(root.get("salary"), request.getSalary()));
            }
            if (request.getHourlyRate() != null) {
                rules.add(reqbuilder.greaterThanOrEqualTo(root.get("hourlyRate"), request.getHourlyRate()));
            }
            if (request.getHireDate() != null) {
                rules.add(reqbuilder.equal(root.get("hireDate"), request.getHireDate()));
            }

            return reqbuilder.and(rules.toArray(new Predicate[0]));
        };
    }
}
