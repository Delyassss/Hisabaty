package com.hisabaty.demo.Student;

import com.hisabaty.demo.School.School;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

enum Status {
    THEORY_TRAINING,
    THEORY_TRAINING_COMPLETED,
    PRACTICAL_TRAINING,
    PRACTICAL_TRAINING_COMPLETED,
    EXAM_READY,
    EXAM_PASSED,
    LICENSED
}

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "students")
@Getter
@Setter
public class Student
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY) //fetch lazy is to not load the school when loading the student
    @JoinColumn(name = "school_id", nullable = false)
    private School school;
    @NotBlank
    private String name;
    @Column(name = "email", unique = true, length = 100)
    private String email;
    @Column(name = "phone", length = 20)
    private String phone;
    @Column(name = "cin", unique = true, nullable = false)
    @NotBlank(message = "CIN is required")
    @Pattern(regexp = "^[A-Z]{1,2}[0-9]{4,6}$", message = "CIN must be 8 digits") // ^  → start of string // [0-9] → any digit (0 through 9) // {8} → exactly 8 times // $ → end of string
    private String cin;
    private Boolean registred = true;
    private Status status = Status.THEORY_TRAINING;

    private Integer RemainingDays ; // don't forget to reset it after each week

    // Modern Java Date types
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime lastTrainingDate;
    private LocalDateTime nextTrainingDate;
    private LocalDate examDate;

    // We can clean up the duplicate tracking dates to keep your DB tidy
    private LocalDate countdownDeadline; // To track the strict 6-month NARSA window

}
