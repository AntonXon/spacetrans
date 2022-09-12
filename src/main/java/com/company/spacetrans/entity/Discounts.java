package com.company.spacetrans.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@JmixEntity
@Table(name = "ST_DISCOUNTS", indexes = {
        @Index(name = "IDX_ST_DISCOUNTS_UNQ", columnList = "GRADE", unique = true)
})
@Entity(name = "st_Discounts")
public class Discounts {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "GRADE", nullable = false)
    @NotNull
    private String grade;

    @Column(name = "VALUE", nullable = false, precision = 19, scale = 2)
    @NotNull
    private BigDecimal value;

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public CustomerGrade getGrade() {
        return grade == null ? null : CustomerGrade.fromId(grade);
    }

    public void setGrade(CustomerGrade grade) {
        this.grade = grade == null ? null : grade.getId();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
