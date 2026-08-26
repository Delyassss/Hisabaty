package com.hisabaty.demo.School;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDate;

@Entity
@Table(name = "schools")
@EntityListeners(AuditingEntityListener.class)
/* 
 * DO NOT USE @Data on entities with relationships (like @ManyToOne).
 * @Data automatically generates @ToString and @EqualsAndHashCode. 
 * If a Book calls its Author, and the Author calls its Books, the generated 
 * toString() will bounce between them infinitely until the server crashes.
 * 
 * Solution: Explicitly use @Getter and @Setter to avoid generating toString().
 */
@Getter
@Setter
public class School
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

    @Column(nullable = false, required = true)
    @NotBlank(message = "Name is required")
        private String name;

    @Column(nullable = false, required = true)
    @NotBlank(message = "Address is required")
        private String address;

    @Column(name = "phone", length = 20 , nullable = false, required = true)
    @NotBlank(message = "Phone is required")
        private String phone;

    @Column(unique = true, nullable = false, required = true)
    @NotBlank(message = "Email is required")
        private String email;

    @Column(nullable = false, required = true)
    @NotBlank(message = "City is required")
        private String city;

    @Column(nullable = false)
    @NotNull(message = "State is required")
        private Boolean state = true;

    @Column(nullable = false, required = true)
    @NotNull(message = "Practice days is required")
    @Min(value = 1, message = "Practice days must be at least 1")
    @Max(value = 7, message = "Practice days must be at most 7")
    private Integer practiceDaysPerWeek = 4 ; // default value is 4
    



}
