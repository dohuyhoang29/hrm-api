package com.aptech.hrmapi.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bank_account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_account_id", unique = true, nullable = false)
    private Long id;
    @Column(name = "company_account_number")
    private String companyAccountNumber; // số tk nhận lương
    @Column(name = "company_account_code")
    private String companyAccountCode; // mã ngân hàng nhận lương
    @Column(name = "company_account_name")
    private String companyAccountName; // tên chủ tài khoản nhận lương
    @Column(name = "bank_branch")
    private String bankBranch; // tên chi nhánh nhận lương
    @Column(name = "personal_account_number")
    private String personalAccountNumber; // số tk cá nhân
    @Column(name = "personal_account_code")
    private String personalAccountCode; // mã tk ngân hàng cá nhân
    @Column(name = "personal_account_name")
    private String personalAccountName; // tên chủ tk cá nhân
    @Column(name = "bank_branch_name")
    private String bankBranchName; // tên chi nhánh ngân hàng cá nhân
    @Column(name = "code")
    private String code; // mã
    @Column(name = "status")
    private Integer status; //trạng thái
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    private Employee employee;
}
