package com.company.spacetrans.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.JmixEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@JmixEntity
@Table(name = "ST_WAYBILL", indexes = {
        @Index(name = "IDX_ST_WAYBILL_CREATOR", columnList = "CREATOR_ID"),
        @Index(name = "IDX_ST_WAYBILL_SHIPPER", columnList = "SHIPPER_ID"),
        @Index(name = "IDX_ST_WAYBILL_CONSIGNEE", columnList = "CONSIGNEE_ID"),
        @Index(name = "IDX_ST_WAYBILL_DEPARTURE_PORT", columnList = "DEPARTURE_PORT_ID"),
        @Index(name = "IDX_STWAYBILL_DESTINATIONPORT", columnList = "DESTINATION_PORT_ID"),
        @Index(name = "IDX_ST_WAYBILL_CARRIER", columnList = "CARRIER_ID")
})
@Entity(name = "st_Waybill")
public class Waybill {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @NotNull
    @Column(name = "REFERENCE", nullable = false)
    private String reference;

    @NotNull
    @JoinColumn(name = "CREATOR_ID", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User creator;

    @JoinColumn(name = "SHIPPER_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Customer shipper;

    @JoinColumn(name = "CONSIGNEE_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Customer consignee;

    @JoinColumn(name = "DEPARTURE_PORT_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Spaceport departurePort;

    @JoinColumn(name = "DESTINATION_PORT_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Spaceport destinationPort;

    @JoinColumn(name = "CARRIER_ID", nullable = false)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Carrier carrier;

    @Composition
    @OneToMany(mappedBy = "waybill", cascade = CascadeType.REMOVE)
    private List<WaybillItem> items;

    @Column(name = "TOTAL_WEIGTH")
    private Double totalWeight;

    @Column(name = "TOTAL_CHARGE", precision = 19, scale = 2)
    private BigDecimal totalCharge;

    public BigDecimal getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(BigDecimal totalCharge) {
        this.totalCharge = totalCharge;
    }

    public Double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(Double totalWeigth) {
        this.totalWeight = totalWeigth;
    }

    public List<WaybillItem> getItems() {
        return items;
    }

    public void setItems(List<WaybillItem> items) {
        this.items = items;
    }

    public Carrier getCarrier() {
        return carrier;
    }

    public void setCarrier(Carrier carrier) {
        this.carrier = carrier;
    }

    public Spaceport getDestinationPort() {
        return destinationPort;
    }

    public void setDestinationPort(Spaceport destinationPort) {
        this.destinationPort = destinationPort;
    }

    public Spaceport getDeparturePort() {
        return departurePort;
    }

    public void setDeparturePort(Spaceport departurePort) {
        this.departurePort = departurePort;
    }

    public Customer getConsignee() {
        return consignee;
    }

    public void setConsignee(Customer consignee) {
        this.consignee = consignee;
    }

    public Customer getShipper() {
        return shipper;
    }

    public void setShipper(Customer shipper) {
        this.shipper = shipper;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
