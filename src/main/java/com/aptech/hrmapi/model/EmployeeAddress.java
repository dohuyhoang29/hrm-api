package com.aptech.hrmapi.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employee_address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_address_id", nullable = false, unique = true)
    private Long id;
    @Column(name = "province_of_births")// 2. Mã tỉnh nơi sinh
    private Integer provinceOfBirths;
    @Column(name = "district_of_birth")//3. Mã huyện nơi sinh
    private Integer districtOfBirth;
    @Column(name = "ward_of_births")//4. Mã phường nơi sinh
    private Integer wardOfBirths;
    @Column(name = "address_of_births")//5. Địa chỉ cụ thể nơi sinh
    private String addressOfBirths;
    @Column(name = "permanent_province")//6. Mã tỉnh thường trú
    private Integer permanentProvince;
    @Column(name = "permanent_district")// 7. Mã huyện thường trú
    private Integer permanentDistrict;
    @Column(name = "permanent_ward")// 8. Mã xã/phường thường trú
    private Integer permanentWard;
    @Column(name = "permanent_address")// 9. Địa chỉ thường trú
    private String permanentAddress;
    @Column(name = "current_province")// 10. Mã tỉnh hiện tại
    private Integer currentProvince;
    @Column(name = "current_district")// 11. Mã quận/huyện hiện tại
    private Integer currentDistrict;
    @Column(name = "current_commune")// 12. Mã xã phường hiện tại
    private Integer currentCommune;
    @Column(name = "current_address")// 13. Địa chỉ hiện tại
    private String currentAddress;
    @Column(name = "status")
    private Integer status;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    private Employee employee;
}
