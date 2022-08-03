package com.globits.da.domain;


import com.globits.core.domain.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "district")
public class District extends BaseObject {
    private static final long serialVersionUID = 1L;
    @Column(name = "code",columnDefinition = "nvarchar(255)",nullable = false)
    private String code;
    @Column(name = "name",columnDefinition = "nvarchar(255)",nullable = false)
    private String name;
//    @ManyToOne
//    @JoinColumn(name = "provice_id")
//    Province province;


}
