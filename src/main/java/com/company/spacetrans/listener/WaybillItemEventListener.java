package com.company.spacetrans.listener;

import com.company.spacetrans.app.service.CalculatorService;
import com.company.spacetrans.entity.Waybill;
import com.company.spacetrans.entity.WaybillItem;
import io.jmix.core.DataManager;
import io.jmix.core.Id;
import io.jmix.core.Metadata;
import io.jmix.core.SaveContext;
import io.jmix.core.event.EntityChangedEvent;
import io.jmix.core.event.EntitySavingEvent;
import io.jmix.core.metamodel.model.MetaProperty;
import liquibase.pro.packaged.P;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@Component("st_WaybillItemEventListener")
public class WaybillItemEventListener {
    @Autowired
    private CalculatorService calculatorService;
    @Autowired
    private DataManager dataManager;

    @EventListener
    public void onWaybillItemSaving(EntitySavingEvent<WaybillItem> event) {
        WaybillItem waybillItem = event.getEntity();
        waybillItem.setCharge(calculatorService.getWaybillItemCharge(waybillItem));
    }

    @EventListener
    public void onWaybillItemBeforeCommit(EntityChangedEvent<WaybillItem> event) {
        Waybill waybill;
        if (event.getType() == EntityChangedEvent.Type.DELETED) {
            Id<Waybill> waybillId = event.getChanges().getOldReferenceId("waybill");
            if (waybillId == null) {
                return;
            }
            waybill = dataManager.load(waybillId).optional().orElse(null);
            if (waybill == null) {
                return;
            }
            //
            int number = 0;
            SaveContext saveContext = new SaveContext();
            List<WaybillItem> items = waybill.getItems();
            items.sort(Comparator.comparing(WaybillItem::getNumber));
            for (WaybillItem item : items) {
                number++;
                item.setNumber(number);
                saveContext.saving(item);
            }
            dataManager.save(saveContext);
        } else {
            WaybillItem waybillItem = dataManager.load(event.getEntityId()).one();
            waybill = waybillItem.getWaybill();
        }
        BigDecimal totalCharge = calculatorService.getTotalCharge(waybill);
        waybill.setTotalCharge(totalCharge);
        Double totalWeight = calculatorService.getTotalWeight(waybill);
        waybill.setTotalWeight(totalWeight);
        dataManager.save(waybill);
    }
}
