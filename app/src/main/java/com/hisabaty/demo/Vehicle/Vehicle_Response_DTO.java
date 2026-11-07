package com.hisabaty.demo.Vehicle;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class Vehicle_Response_DTO {

    private Long id;
    private Long schoolId;
    private String schoolName;
    private String brand;
    private String model;
    private String plateNumber;
    private String typeOfLicense;
    private Integer year;
    private VehicleStatus status;
    private Integer mileage;
    private LocalDate insuranceExpiry;
    private LocalDate technicalVisitExpiry;
    private LocalDateTime createdAt;

    public static Vehicle_Response_DTO toVehicleResponseDTO(Vehicle vehicle) {
        if (vehicle == null) {
            return null;
        }
        Vehicle_Response_DTO dto = new Vehicle_Response_DTO();
        dto.setId(vehicle.getId());
        if (vehicle.getSchool() != null) {
            dto.setSchoolId(vehicle.getSchool().getId());
            dto.setSchoolName(vehicle.getSchool().getName());
        }
        dto.setBrand(vehicle.getBrand());
        dto.setModel(vehicle.getModel());
        dto.setPlateNumber(vehicle.getPlateNumber());
        dto.setTypeOfLicense(vehicle.getTypeOfLicense());
        dto.setYear(vehicle.getYear());
        dto.setStatus(vehicle.getStatus());
        dto.setMileage(vehicle.getMileage());
        dto.setInsuranceExpiry(vehicle.getInsuranceExpiry());
        dto.setTechnicalVisitExpiry(vehicle.getTechnicalVisitExpiry());
        dto.setCreatedAt(vehicle.getCreatedAt());
        return dto;
    }

    public static Page<Vehicle_Response_DTO> toVehicleResponseDTO_All(Page<Vehicle> vehicles) {
        if (vehicles == null) {
            return null;
        }
        return vehicles.map(Vehicle_Response_DTO::toVehicleResponseDTO);
    }
}
