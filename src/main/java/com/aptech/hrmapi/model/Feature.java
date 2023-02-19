package com.aptech.hrmapi.model;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "feature")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Feature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feature_id", nullable = false, unique = true)
    private Long id;

    @Column(name = "feature_code", nullable = false, unique = true)
    private String featureCode;

    @Column(name = "feature_name", nullable = false)
    private String featureName;

    @Column(name = "feature_url", nullable = false, unique = true)
    private String featureURL;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "created_date", nullable = false)
    private Date createdDate;

    @Column(name = "modified_by", nullable = false)
    private String modifiedBy;

    @Column(name = "modified_date", nullable = false)
    private Date modifiedDate;
}
