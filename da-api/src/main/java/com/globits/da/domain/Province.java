package com.globits.da.domain;

import com.globits.core.domain.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "province")
public class Province extends BaseObject {
    private static final long serialVersionUID = 1L;
    @Column(name = "code",columnDefinition = "nvarchar(255)",nullable = false)
    private String code;
    @Column(name = "name",columnDefinition = "nvarchar(255)",nullable = false)
    private String name;
//    @OneToMany(mappedBy = "province")
//    private Set<District> districts;
}
