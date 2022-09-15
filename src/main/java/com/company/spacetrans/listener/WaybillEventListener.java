package com.company.spacetrans.listener;

import com.company.spacetrans.app.service.CalculatorService;
import com.company.spacetrans.entity.Waybill;
import com.company.spacetrans.entity.WaybillItem;
import io.jmix.core.event.EntityChangedEvent;
import io.jmix.core.event.EntitySavingEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component("st_WaybillEventListener")
public class WaybillEventListener {
    @Autowired
    private CalculatorService calculatorService;

    @EventListener
    public void onWaybillSaving(EntitySavingEvent<Waybill> event) {
        Waybill waybill = event.getEntity();
        waybill.setTotalCharge(calculatorService.getTotalCharge(waybill));
    }
}
