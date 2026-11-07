package com.hisabaty.demo.Vehicle;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class VehicleSpecification {

    public static Specification<Vehicle> searchVehicle(Vehicle_Request_DTO request, Long authenticatedSchoolId) {
        return (root, query, reqbuilder) -> {
            List<Predicate> rules = new ArrayList<>();

            if (authenticatedSchoolId != null) {
                rules.add(reqbuilder.equal(root.get("school").get("id"), authenticatedSchoolId));
            }

            if (request == null) {
                return reqbuilder.and(rules.toArray(new Predicate[0]));
            }

            if (StringUtils.hasText(request.getBrand())) {
                rules.add(reqbuilder.like(reqbuilder.lower(root.get("brand")), request.getBrand().toLowerCase() + "%"));
            }
            if (StringUtils.hasText(request.getModel())) {
                rules.add(reqbuilder.like(reqbuilder.lower(root.get("model")), request.getModel().toLowerCase() + "%"));
            }
            if (StringUtils.hasText(request.getPlateNumber())) {
                rules.add(reqbuilder.like(reqbuilder.lower(root.get("plateNumber")), request.getPlateNumber().toLowerCase() + "%"));
            }
            if (StringUtils.hasText(request.getTypeOfLicense())) {
                rules.add(reqbuilder.equal(root.get("typeOfLicense"), request.getTypeOfLicense()));
            }
            if (request.getYear() != null) {
                rules.add(reqbuilder.equal(root.get("year"), request.getYear()));
            }
            if (request.getStatus() != null) {
                rules.add(reqbuilder.equal(root.get("status"), request.getStatus()));
            }
            if (request.getMileage() != null) {
                rules.add(reqbuilder.greaterThanOrEqualTo(root.get("mileage"), request.getMileage()));
            }
            if (request.getInsuranceExpiry() != null) {
                rules.add(reqbuilder.lessThanOrEqualTo(root.get("insuranceExpiry"), request.getInsuranceExpiry()));
            }
            if (request.getTechnicalVisitExpiry() != null) {
                rules.add(reqbuilder.lessThanOrEqualTo(root.get("technicalVisitExpiry"), request.getTechnicalVisitExpiry()));
            }

            return reqbuilder.and(rules.toArray(new Predicate[0]));
        };
    }
}
