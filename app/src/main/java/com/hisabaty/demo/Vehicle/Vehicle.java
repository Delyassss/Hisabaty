package com.hisabaty.demo.Vehicle;

import com.hisabaty.demo.School.School;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "vehicles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE vehicles SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_entity", nullable = false)
    private School school = new School();

    @NotBlank(message = "Brand is required")
    @Column(nullable = false)
    private String brand;

    @NotBlank(message = "Model is required")
    @Column(nullable = false)
    private String model;

    @NotBlank(message = "Plate number is required")
    @Column(name = "plate_number", unique = true, nullable = false)
    @Pattern(regexp = "^[0-9]{1,6}-[A-Z]{1,2}-[0-9]{1,2}$", message = "Invalid Moroccan plate format")
    private String plateNumber;

    @Column(name = "type_of_license", nullable = false)
    @NotBlank(message = "Type of license is required")
    private String typeOfLicense;

    @Column(name = "year")
    private Integer year;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private VehicleStatus status = VehicleStatus.AVAILABLE;

    @Column(name = "mileage")
    private Integer mileage = 0;

    @Column(name = "insurance_expiry")
    private LocalDate insuranceExpiry;

    @Column(name = "technical_visit_expiry")
    private LocalDate technicalVisitExpiry;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private Boolean deleted = false;
}
