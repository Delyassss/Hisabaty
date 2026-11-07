package com.hisabaty.demo.Vehicle;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Vehicle_Request_DTO {

    @NotNull(message = "School ID is required")
    @Min(value = 1, message = "ID must be positive")
    private Long schoolId;

    @NotBlank(message = "Brand is required")
    private String brand;

    @NotBlank(message = "Model is required")
    private String model;

    @NotBlank(message = "Plate number is required")
    @Pattern(regexp = "^[0-9]{1,6}-[A-Z]{1,2}-[0-9]{1,2}$", message = "Invalid Moroccan plate format")
    private String plateNumber;

    @NotBlank(message = "Type of license is required")
    private String typeOfLicense;

    private Integer year;

    private VehicleStatus status;

    @Min(value = 0, message = "Mileage cannot be negative")
    private Integer mileage;

    private LocalDate insuranceExpiry;

    private LocalDate technicalVisitExpiry;

    private LocalDateTime createdAt;
}
