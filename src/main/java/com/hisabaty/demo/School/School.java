package com.hisabaty.demo.School;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "schools")
@Data // if you have an object that call this class you need to use Getter and Setter cuz data will end up with infinite loop
public class School
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Name is required")
        private String name;

    @Column(nullable = false)
    @NotBlank(message = "Address is required")
        private String address;

    @Column(name = "phone", length = 20)
    @NotBlank(message = "Phone is required")
        private String phone;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Email is required")
        private String email;

    @Column(nullable = false)
        private String city;

    @Column(nullable = false)
        private Boolean state = true;

    @Column(nullable = false)
    private Integer practiceDays;
}
