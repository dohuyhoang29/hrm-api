package com.aptech.hrmapi.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "employee")
@Builder
@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id", unique = true, nullable = false)
    private Integer id;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "personal_email")
    private String personalEmail;
    @Column(name = "vissoft_email")
    private String vissoftEmail;
    @Column(name = "image")
    private String image;
    @Column(name = "hire_date")
    private Date hireDate;
    @Column(name = "department_id")
    private Integer department;
    @Column(name = "employee_code")
    private String employeeCode;
    @Column(name = "manager_id")
    private Integer managerId;
    @Column(name = "relative_phone")
    private String relativePhone;
    @Column(name = "relationship")
    private String relationship;
    @Column(name = "relative_name")
    private String relativeName;
    @Column(name = "status")
    private Integer status;
    @Column(name = "day_off")
    private Date dayOff;
    @ManyToOne
    @JoinColumn(name = "title_id")
    private Title title;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Employee employee = (Employee) o;
        return id != null && Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
