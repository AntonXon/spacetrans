package com.company.spacetrans.screen.waybillitem;

import com.company.spacetrans.app.service.CalculatorService;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.component.TextField;
import io.jmix.ui.component.TextInputField;
import io.jmix.ui.screen.*;
import com.company.spacetrans.entity.WaybillItem;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

@UiController("st_WaybillItem.edit")
@UiDescriptor("waybill-item-edit.xml")
@EditedEntityContainer("waybillItemDc")
public class WaybillItemEdit extends StandardEditor<WaybillItem> {
    @Autowired
    private CalculatorService calculatorService;

    @Autowired
    private TextField<BigDecimal> chargeField;

    @Subscribe("dimLengthField")
    public void onDimLengthFieldValueChange(HasValue.ValueChangeEvent<Double> event) {
        updateCharge();
    }

    @Subscribe("dimWidthField")
    public void onDimWidthFieldValueChange(HasValue.ValueChangeEvent<Double> event) {
        updateCharge();
    }

    @Subscribe("dimHeightField")
    public void onDimHeightFieldValueChange(HasValue.ValueChangeEvent<Double> event) {
        updateCharge();
    }

    @Subscribe("weightField")
    public void onWeightFieldValueChange(HasValue.ValueChangeEvent<Double> event) {
        updateCharge();
    }

    private void updateCharge() {
        chargeField.setValue(calculatorService.getWaybillItemCharge(getEditedEntity()));
    }


}
