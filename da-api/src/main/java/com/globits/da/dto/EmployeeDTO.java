package com.globits.da.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeDTO {
    private Long id;
    private String code;
    private String name;
    private String email;
    private String phone;
    private int age;
}
