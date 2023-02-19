package com.aptech.hrmapi.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "employee_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_detail_id", unique = true, nullable = false)
    private Long id;
    @Column(name = "date_of_birt")
    private Date dateOfBirt;
    @Column(name = "gender")
    private Boolean gender;
    @Column(name = "cmnd_cccd")
    private String cmnd;
    @Column(name = "cmnd_issue_date")
    private Date cmndIssueDate;
    @Column(name = "cmnd_issued_by")
    private String cmndIssuaBy;
    @Column(name = "marriage")
    private String marriage;
    @Column(name = "ethnic")
    private String ethnic;
    @Column(name = "nationality")
    private String nationality;
    @Column(name = "origin_location")
    private Integer originLocation;
    @Column(name = "place_of_birth")
    private Integer placeOfBirth;
    @Column(name = "resident")
    private Integer resident;
    @Column(name = "current_address")
    private Integer currentAddress;
    @Column(name = "status")
    private Integer status;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    private Employee employee;
}
