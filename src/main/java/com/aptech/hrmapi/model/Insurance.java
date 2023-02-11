package com.aptech.hrmapi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "insurance")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Insurance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "insurance_id", nullable = false, unique = true)
    private Long id;

    @Column(name = "insurance_no")
    private String insuranceNo;

    @Column(name = "medical_card_no")
    private String cartNo;

    @Column(name = "keep_by_company")
    private String kepper;

    @Column(name = "shk_number")
    private String shkNumber;

    @Column(name = "province_scb")
    private String provinceSCB;

    @Column(name = "province_kcb")
    private String provinceKCB;

    @Column(name = "status")
    private Integer status;

    @Column(name = "code")
    private String code;

    @Column(name = "start_date_applied")
    private Date startDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    private Employee employee;
}
