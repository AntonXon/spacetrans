package com.company.spacetrans.screen.waybill;

import com.company.spacetrans.entity.*;
import com.company.spacetrans.screen.company.CompanyBrowse;
import com.company.spacetrans.screen.individual.IndividualBrowse;
import io.jmix.core.DataManager;
import io.jmix.core.Entity;
import io.jmix.core.LoadContext;
import io.jmix.ui.action.entitypicker.EntityLookupAction;
import io.jmix.ui.component.ComboBox;
import io.jmix.ui.component.EntityPicker;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.component.RadioButtonGroup;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import liquibase.pro.packaged.I;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import java.util.*;

@UiController("st_Waybill.edit")
@UiDescriptor("waybill-edit.xml")
@EditedEntityContainer("waybillDc")
public class WaybillEdit extends StandardEditor<Waybill> {
    private final String COMPANY_TYPE = "Company";
    private final String INDIVIDUAL_TYPE = "Individual";

    @Autowired
    private DataManager dataManager;
    @Autowired
    private CollectionLoader<Customer> customersDl;
    @Autowired
    private RadioButtonGroup<String> shipperType;
    @Autowired
    private RadioButtonGroup<String> consigneeType;
    @Autowired
    private ComboBox<Customer> shipperField;
    @Named("consigneeField.entityLookup")
    private EntityLookupAction<Customer> consigneeFieldEntityLookup;
    @Autowired
    private ComboBox<AstronomicalBody> departurePlaceField;
    @Autowired
    private ComboBox<AstronomicalBody> destinationPlaceField;
    @Autowired
    private EntityPicker<Spaceport> destinationPortField;
    @Autowired
    private EntityPicker<Spaceport> departurePortField;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        Map<String, String> types = Map.of(
                COMPANY_TYPE, COMPANY_TYPE,
                INDIVIDUAL_TYPE, INDIVIDUAL_TYPE
        );
        //
        shipperType.setOptionsMap(types);
        if (getEditedEntity().getShipper() == null) {
            shipperType.setValue(COMPANY_TYPE);
        } else {
            if (getEditedEntity().getShipper() instanceof Company) {
                shipperType.setValue(COMPANY_TYPE);
            } else {
                shipperType.setValue(INDIVIDUAL_TYPE);
            }
        }
        //
        consigneeType.setOptionsMap(types);
        if (getEditedEntity().getConsignee() == null) {
            consigneeType.setValue(COMPANY_TYPE);
        } else {
            if (getEditedEntity().getConsignee() instanceof Company) {
                consigneeType.setValue(COMPANY_TYPE);
            } else {
                consigneeType.setValue(INDIVIDUAL_TYPE);
            }
        }
    }

    @Subscribe("shipperType")
    public void onShipperTypeValueChange(HasValue.ValueChangeEvent<String> event) {
        if (Objects.equals(event.getValue(), COMPANY_TYPE)) {
            customersDl.setParameter("class", Company.class);
        } else if (Objects.equals(event.getValue(), INDIVIDUAL_TYPE)) {
            customersDl.setParameter("class", Individual.class);
        }
        customersDl.load();
    }

    @Subscribe(id = "customersDc", target = Target.DATA_CONTAINER)
    public void onCustomersDcCollectionChange(CollectionContainer.CollectionChangeEvent<Customer> event) {
        shipperField.setOptionsList(event.getSource().getItems());
    }

    @Subscribe("consigneeType")
    public void onConsigneeTypeValueChange(HasValue.ValueChangeEvent<String> event) {
        if (Objects.equals(event.getValue(), COMPANY_TYPE)) {
            consigneeFieldEntityLookup.setScreenClass(CompanyBrowse.class);
        } else if (Objects.equals(event.getValue(), INDIVIDUAL_TYPE)) {
            consigneeFieldEntityLookup.setScreenClass(IndividualBrowse.class);
        }
    }

    @Install(to = "astronomicalBodiesDl", target = Target.DATA_LOADER)
    private List<AstronomicalBody> astronomicalBodiesDlLoadDelegate(LoadContext<AstronomicalBody> loadContext) {
        List<AstronomicalBody> astronomicalBodies = new ArrayList<>();
        astronomicalBodies.addAll(dataManager.load(Moon.class)
                .all()
                .list());
        astronomicalBodies.addAll(dataManager.load(Planet.class)
                .all()
                .list());
        return astronomicalBodies;
    }

    @Subscribe(id = "astronomicalBodiesDc", target = Target.DATA_CONTAINER)
    public void onAstronomicalBodiesDcCollectionChange(CollectionContainer.CollectionChangeEvent<AstronomicalBody> event) {
        departurePlaceField.setOptionsList(event.getSource().getItems());
        destinationPlaceField.setOptionsList(event.getSource().getItems());
    }

    @Subscribe("departurePlaceField")
    public void onDeparturePlaceFieldValueChange(HasValue.ValueChangeEvent<AstronomicalBody> event) {
        if (!event.isUserOriginated() || event.getValue() == null) {
            return;
        }
        setPort(event.getValue().getId(), departurePortField);
    }

    @Subscribe("destinationPlaceField")
    public void onDestinationPlaceFieldValueChange(HasValue.ValueChangeEvent<AstronomicalBody> event) {
        if (!event.isUserOriginated() || event.getValue() == null) {
            return;
        }
        setPort(event.getValue().getId(), destinationPortField);
    }


    private void setPort(UUID id, EntityPicker<Spaceport> entityPicker) {
        entityPicker.setValue(null);
        dataManager.load(Spaceport.class)
                .query("select e from st_Spaceport e " +
                        "where e.isDefault = true and (e.planet.id = :id or e.moon.id = :id)")
                .parameter("id", id)
                .optional().ifPresent(entityPicker::setValue);
    }


}
