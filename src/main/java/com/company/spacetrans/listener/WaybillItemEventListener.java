package com.company.spacetrans.listener;

import com.company.spacetrans.app.service.CalculatorService;
import com.company.spacetrans.entity.WaybillItem;
import io.jmix.core.event.EntityChangedEvent;
import io.jmix.core.event.EntitySavingEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component("st_WaybillItemEventListener")
public class WaybillItemEventListener {
    @Autowired
    private CalculatorService calculatorService;

    @EventListener
    public void onWaybillItemSaving(EntitySavingEvent<WaybillItem> event) {
        WaybillItem waybillItem = event.getEntity();
        waybillItem.setCharge(calculatorService.getWaybillItemCharge(waybillItem));
    }

    @EventListener
    public void onWaybillItemBeforeCommit(EntityChangedEvent<WaybillItem> event) {
        if (event.getType() == EntityChangedEvent.Type.DELETED) {

        }
    }
}
