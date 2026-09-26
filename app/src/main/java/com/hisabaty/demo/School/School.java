package com.hisabaty.demo.School;

import java.util.List;

import org.hibernate.annotations.SQLDelete;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotNull;

import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.hisabaty.demo.Student.Student;


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
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE students SET deleted = true Where id = ?")
@SQLRestriction("deleted = false")

public class School
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

    @OneToMany(mappedBy = "school", fetch = FetchType.EAGER) // the mappedBy basically to point to the student 
    private List<Student> students; // the OneToMany use fetch LAZY by default

    @Column(nullable = false)
    @NotBlank(message = "Name is required")
        private String name;

    @Column(nullable = false)
    @NotBlank(message = "Address is required")
        private String address;

    @Column(name = "phone", length = 20 , nullable = false)
    @NotBlank(message = "Phone is required")
        private String phone;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Email is required")
        private String email;

    @Column(nullable = false)
    @NotBlank(message = "City is required")
        private String city;

    @Column(nullable = false)
    @NotNull(message = "State is required") 
        private Boolean state = true;

    @Column(nullable = false)
    @NotNull(message = "Practice days is required")
    @Min(value = 1, message = "Practice days must be at least 1")
    @Max(value = 7, message = "Practice days must be at most 7")
    private Integer practiceDaysPerWeek = 4 ; // default value is 4


    @Column(name = "license_type") // this is the name of the column that will store the licenses
    private List<String> licenseAvailable;

    @Column(name = "student_count")
    private Integer studentCount = 0;
    
    Integer  studentIndex = -1;
    String  studentCinPrefix;

    private Boolean deleted = false;
    
    
    
    



}
