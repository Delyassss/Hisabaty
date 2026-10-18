package com.hisabaty.demo.Instructor;

import com.hisabaty.demo.School.School;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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
@Table(name = "instructors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE instructors SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_entity", nullable = false)
    private School school = new School();

    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    private String name;

    @Column(unique = true, length = 100, nullable = false)
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email address")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Phone is required")
    @Column(name = "phone", length = 20, nullable = false)
    @Pattern(regexp = "^[0-9]{8,15}$", message = "Invalid phone number format")
    private String phone;

    @Column(name = "cin", unique = true, nullable = false)
    @NotBlank(message = "CIN is required")
    @Pattern(regexp = "^[A-Z]{1,2}[0-9]{4,6}$", message = "Invalid Moroccan CIN format")
    private String cin;

    @Column(name = "cap_number")
    private String capNumber; // Professional license / authorization number (CAPP)

    @Column(name = "type_of_license", nullable = false)
    @NotBlank(message = "Type of license is required")
    private String typeOfLicense;

    @Enumerated(EnumType.STRING)
    @Column(name = "speciality")
    private InstructorSpeciality speciality = InstructorSpeciality.BOTH;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private InstructorStatus status = InstructorStatus.ACTIVE;

    @Column(name = "salary")
    private Double salary = 0.0;

    @Column(name = "hourly_rate")
    private Double hourlyRate = 0.0;

    @PastOrPresent(message = "Hire date must be in the past or present")
    @Column(name = "hire_date")
    private LocalDate hireDate = LocalDate.now();

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private Boolean deleted = false;
}
