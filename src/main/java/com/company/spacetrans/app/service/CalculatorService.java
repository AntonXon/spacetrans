package com.company.spacetrans.app.service;

import com.company.spacetrans.entity.*;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component("st_CalculatorService")
public class CalculatorService {
    @Autowired
    private DataManager dataManager;

    //
    public BigDecimal getWaybillItemCharge(WaybillItem waybillItem) {
        double height = 0.0;
        double width = 0.0;
        double length = 0.0;
        double weight = 0.0;
        if (waybillItem.getDim() != null) {
            Dimensions dimensions = waybillItem.getDim();
            if (dimensions.getLength() != null) {
                length = dimensions.getLength();
            }
            if (dimensions.getWidth() != null) {
                width = dimensions.getWidth();
            }
            if (dimensions.getHeight() != null) {
                height = dimensions.getHeight();
            }
        }
        if (waybillItem.getWeight() != null) {
            weight = waybillItem.getWeight();
        }
        double dimSum = width + height + length;
        return BigDecimal.valueOf(dimSum * 2.0 + weight * 1.5);
    }

    //
    public Double getTotalWeight(Waybill waybill) {
        Double totalWeight = waybill.getItems().stream()
                .map(WaybillItem::getWeight)
                .reduce(0.0, Double::sum);
        return totalWeight;
    }

    //
    public BigDecimal getTotalCharge(Waybill waybill) {
        BigDecimal charge = waybill.getItems().stream()
                .map(WaybillItem::getCharge)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal discount = getDiscount(waybill.getShipper());
        BigDecimal discountValue = charge.multiply(discount).divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
        charge = charge.subtract(discountValue);
        return charge;
    }

    //
    public BigDecimal getDiscount(Customer customer) {
        if (customer.getGrade() == null) {
            return BigDecimal.ZERO;
        }
        CustomerGrade grade = customer.getGrade();
        Discounts discount = dataManager.load(Discounts.class)
                .query("select e from st_Discounts e where e.grade = :grade")
                .parameter("grade", grade)
                .optional()
                .orElse(null);
        if (discount == null) {
            return BigDecimal.ZERO;
        }
        return discount.getValue();
    }

}
