package com.hisabaty.demo.Vehicle;

import lombok.Data;

import java.time.LocalDate;

@Data
public class VehicleSearchCriteria {
    private Long id;
    private Long schoolId;
    private String brand;
    private String model;
    private String plateNumber;
    private String typeOfLicense;
    private Integer year;
    private VehicleStatus status;
    private Integer mileage;
    private LocalDate insuranceExpiry;
    private LocalDate technicalVisitExpiry;
}
