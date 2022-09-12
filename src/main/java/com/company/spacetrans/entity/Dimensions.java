package com.company.spacetrans.entity;

import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@JmixEntity(name = "st_Dimensions")
@Embeddable
public class Dimensions {
    @Column(name = "LENGTH", nullable = false)
    @NotNull
    private Double length;

    @NotNull
    @Column(name = "WIDTH", nullable = false)
    private Double width;

    @NotNull
    @Column(name = "HEIGHT", nullable = false)
    private Double height;

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getWidth() {
        return width;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getHeight() {
        return height;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }
}
