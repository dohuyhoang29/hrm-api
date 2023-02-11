package com.aptech.hrmapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "personal_income_tax")
@Getter
@Setter
public class UserTax {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "personal_income_tax_id")
    private Integer id;

    @Column(name = "tax_code")
    private String taxCode;

    @Column(name = "number_of_dependents")
    private Integer numberDependents;

    @Column(name = "status")
    private Integer status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    private Employee employee;
}
