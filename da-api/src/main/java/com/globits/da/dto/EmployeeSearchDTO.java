package com.globits.da.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeSearchDTO {
    private String keyword;
    private String orderBy;
    private Integer pageSize;
    private Integer pageIndex;

}