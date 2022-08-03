package com.globits.da.domain;

import com.globits.core.domain.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "certificate")
public class Certificate extends BaseObject {
    @Column(name = "name")
    private String name;
    @Column(name = "grantedby")
    private String grantedBy;
    @Temporal(TemporalType.DATE)
    @Column(name = "dateStartEffect")
    private Date  dateStartEffect;
    @Temporal(TemporalType.DATE)
    @Column(name = "dateEndEffect")
    private Date  dateEndEffect;
}
