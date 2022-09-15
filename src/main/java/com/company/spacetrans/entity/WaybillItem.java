package com.company.spacetrans.entity;

import io.jmix.core.entity.annotation.EmbeddedParameters;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@JmixEntity
@Table(name = "ST_WAYBILL_ITEM", indexes = {
        @Index(name = "IDX_ST_WAYBILL_ITEM_UNQ_1", columnList = "NAME", unique = true),
        @Index(name = "IDX_ST_WAYBILL_ITEM_WAYBILL", columnList = "WAYBILL_ID")
})
@Entity(name = "st_WaybillItem")
public class WaybillItem {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "NUMBER", nullable = false)
    @NotNull
    private Integer number;

    @InstanceName
    @Column(name = "NAME", nullable = false)
    @NotNull
    private String name;

    @Column(name = "WEIGHT", nullable = false)
    @NotNull
    private Double weight;

    @EmbeddedParameters(nullAllowed = false)
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "length", column = @Column(name = "DIM_LENGTH", nullable = false)),
            @AttributeOverride(name = "width", column = @Column(name = "DIM_WIDTH", nullable = false)),
            @AttributeOverride(name = "height", column = @Column(name = "DIM_HEIGHT", nullable = false))
    })
    private Dimensions dim;

    @Column(name = "CHARGE", precision = 19, scale = 2)
    private BigDecimal charge;

    @JoinColumn(name = "WAYBILL_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Waybill waybill;

    public Waybill getWaybill() {
        return waybill;
    }

    public void setWaybill(Waybill waybill) {
        this.waybill = waybill;
    }

    public BigDecimal getCharge() {
        return charge;
    }

    public void setCharge(BigDecimal charge) {
        this.charge = charge;
    }

    public Dimensions getDim() {
        return dim;
    }

    public void setDim(Dimensions dim) {
        this.dim = dim;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
