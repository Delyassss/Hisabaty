package com.hisabaty.demo.Vehicle;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Vehicle_Patch_Request_DTO {

    @Min(value = 1, message = "ID must be positive")
    private Long schoolId;

    private String brand;

    private String model;

    @Pattern(regexp = "^[0-9]{1,6}-[A-Z]{1,2}-[0-9]{1,2}$", message = "Invalid Moroccan plate format")
    private String plateNumber;

    private String typeOfLicense;

    private Integer year;

    private VehicleStatus status;

    @Min(value = 0, message = "Mileage cannot be negative")
    private Integer mileage;

    private LocalDate insuranceExpiry;

    private LocalDate technicalVisitExpiry;
}
