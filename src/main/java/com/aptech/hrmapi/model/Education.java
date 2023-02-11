package com.aptech.hrmapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "education")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "education_id", unique = true, nullable = false)
    private Long id;
    @Column(name = "education")
    private String edu;
    @Column(name = "school")
    private String school;
    @Column(name = "majoring")
    private String majoring;
    @Column(name = "certificate")
    private String certificate;
    @Column(name = "status")
    private Integer status;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    private Employee employee;
}
