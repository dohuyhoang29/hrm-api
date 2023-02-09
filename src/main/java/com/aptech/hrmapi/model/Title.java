package com.aptech.hrmapi.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;
import java.util.Set;

@Builder
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "title")
public class Title {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(columnDefinition = "nvarchar(100)", name = "name")
    private String name;

    @Column(columnDefinition = "nvarchar(100)", name = "description")
    private String description;

    @Column(name = "status")
    private Integer status;

    @Column(name = "title_code")
    private String code;

    @OneToMany(mappedBy = "title", cascade = CascadeType.ALL)
    private Set<Employee> employees;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Title title = (Title) o;
        return id != null && Objects.equals(id, title.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
