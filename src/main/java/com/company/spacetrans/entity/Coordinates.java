package com.company.spacetrans.entity;

import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

@JmixEntity(name = "st_Coordinates")
@Embeddable
public class Coordinates {
    @Digits(integer = 3, fraction = 6)
    @NotNull
    @Column(name = "LATITUDE", nullable = false)
    private Double latitude;

    @Digits(integer = 3, fraction = 6)
    @Column(name = "LONGITUDE", nullable = false)
    @NotNull
    private Double longitude;

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
