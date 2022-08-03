package com.globits.da.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "employee")
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "code", columnDefinition = "nvarchar(100)")
    private String code;
    @Column(name = "name", columnDefinition = "nvarchar(100)")
    private String name;
    @Column(name = "email", columnDefinition = "nvarchar(100)")
    private String email;
    @Column(name = "phone", columnDefinition = "nvarchar(100)")
    private String phone;
    @Column(name = "age", columnDefinition = "int(11)")
    private int age;
}
