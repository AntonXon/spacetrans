package com.company.spacetrans.screen.waybill;

import com.company.spacetrans.app.service.CalculatorService;
import com.company.spacetrans.entity.*;
import com.company.spacetrans.screen.company.CompanyBrowse;
import com.company.spacetrans.screen.individual.IndividualBrowse;
import io.jmix.core.DataManager;
import io.jmix.core.LoadContext;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.ui.action.Action;
import io.jmix.ui.action.entitypicker.EntityLookupAction;
import io.jmix.ui.component.*;
import io.jmix.ui.model.*;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Named;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@UiController("st_Waybill.edit")
@UiDescriptor("waybill-edit.xml")
@EditedEntityContainer("waybillDc")
public class WaybillEdit extends StandardEditor<Waybill> {
    private final String COMPANY_TYPE = "Company";
    private final String INDIVIDUAL_TYPE = "Individual";

    @Autowired
    private DataManager dataManager;
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @Autowired
    private CalculatorService calculatorService;
    @Autowired
    private CollectionLoader<Customer> customersDl;
    @Autowired
    private RadioButtonGroup<String> shipperType;
    @Autowired
    private RadioButtonGroup<String> consigneeType;
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
    @Autowired
    private CollectionPropertyContainer<WaybillItem> itemsDc;
    @Autowired
    private TextField<Double> totalWeightField;
    @Autowired
    private Table<WaybillItem> itemsTable;
    @Autowired
    private TextField<BigDecimal> totalChargeField;
    @Autowired
    private Label<String> discountLabel;
    @Autowired
    private ComboBox<Carrier> carrierField;

    @Subscribe
    public void onInitEntity(InitEntityEvent<Waybill> event) {
        event.getEntity().setCreator((User) currentAuthentication.getUser());
    }

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
        //
        setDiscount();
        //
        setPlace(getEditedEntity().getDeparturePort(), departurePlaceField);
        setPlace(getEditedEntity().getDestinationPort(), destinationPlaceField);
        //
        carrierField.setOptionsList(getAvailableCarriers());
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

    @Install(to = "itemsTable.create", subject = "newEntitySupplier")
    private WaybillItem itemsTableCreateNewEntitySupplier() {
        WaybillItem waybillItem = dataManager.create(WaybillItem.class);
        waybillItem.setWaybill(getEditedEntity());
        waybillItem.setNumber(getEditedEntity().getItems() == null ? 1 : getEditedEntity().getItems().size() + 1);
        return waybillItem;
    }

    @Subscribe("itemsTable.itemDown")
    public void onItemsTableItemDown(Action.ActionPerformedEvent event) {
        moveItem(false);
    }

    @Subscribe("itemsTable.itemUp")
    public void onItemsTableItemUp(Action.ActionPerformedEvent event) {
        moveItem(true);
    }

    @Subscribe(id = "itemsDc", target = Target.DATA_CONTAINER)
    public void onItemsDcCollectionChange(CollectionContainer.CollectionChangeEvent<WaybillItem> event) {
        if (event.getChangeType().equals(CollectionChangeType.REMOVE_ITEMS)) {
            WaybillItem deletedWaybillItem = event.getChanges().iterator().next();
            int deletedNumber = deletedWaybillItem.getNumber();
            List<WaybillItem> waybillItemList = itemsDc.getItems();
            for (WaybillItem waybillItem : waybillItemList) {
                if (waybillItem.getNumber() > deletedNumber) {
                    waybillItem.setNumber(waybillItem.getNumber() - 1);
                }
            }
        }
        totalWeightField.setValue(calculatorService.getTotalWeight(getEditedEntity()));
        totalChargeField.setValue(calculatorService.getTotalCharge(getEditedEntity()));
    }

    @Subscribe("shipperField")
    public void onShipperFieldValueChange(HasValue.ValueChangeEvent<Customer> event) {
        if (event.isUserOriginated()) {
            setDiscount();
            totalChargeField.setValue(calculatorService.getTotalCharge(getEditedEntity()));
        }
    }

    @Subscribe("departurePortField")
    public void onDeparturePortFieldValueChange(HasValue.ValueChangeEvent<Spaceport> event) {
        updateCarrierValue();
    }

    @Subscribe("destinationPortField")
    public void onDestinationPortFieldValueChange(HasValue.ValueChangeEvent<Spaceport> event) {
        updateCarrierValue();
    }

    private void updateCarrierValue() {
        List<Carrier> carriers = getAvailableCarriers();
        carrierField.setOptionsList(carriers);
        if (!carriers.contains(carrierField.getValue())) {
            carrierField.clear();
        }
    }

    private List<Carrier> getAvailableCarriers() {
        List<Carrier> listDep = dataManager
                .load(Carrier.class)
                .query("select e from st_Carrier e join e.ports p where p = :port")
                .parameter("port", getEditedEntity().getDeparturePort())
                .list();
        List<Carrier> listDest = dataManager
                .load(Carrier.class)
                .query("select e from st_Carrier e join e.ports p where p = :port")
                .parameter("port", getEditedEntity().getDestinationPort())
                .list();
        List<Carrier> intersect = listDep.stream()
                .distinct()
                .filter(listDest::contains)
                .collect(Collectors.toList());
        return intersect;
    }

    private void setPlace(Spaceport port, ComboBox<AstronomicalBody> field) {
        if (port != null) {
            AstronomicalBody astronomicalBody = null;
            if (port.getPlanet() != null) {
                astronomicalBody = port.getPlanet();
            } else if (port.getMoon() != null) {
                astronomicalBody = port.getMoon();
            }
            if (astronomicalBody != null) {
                field.setValue(astronomicalBody);
            }
        }
    }

    private void moveItem(boolean up) {
        WaybillItem waybillItem = itemsTable.getSingleSelected();
        if (waybillItem != null) {
            int currentPosition = waybillItem.getNumber();
            int newPosition = currentPosition;
            if (up) {
                newPosition--;
            } else {
                newPosition++;
            }
            if (newPosition == 0 || newPosition > itemsDc.getItems().size()) {
                return;
            }
            for (WaybillItem item : itemsDc.getItems()) {
                if (item.equals(waybillItem)) {
                    continue;
                }
                if (item.getNumber() == newPosition) {
                    item.setNumber(currentPosition);
                }
            }
            waybillItem.setNumber(newPosition);
            itemsTable.sort("number", Table.SortDirection.ASCENDING);
        }
    }

    private void setDiscount() {
        if (getEditedEntity().getShipper() != null) {
            BigDecimal discount = calculatorService.getDiscount(getEditedEntity().getShipper());
            discountLabel.setValue("Discount value " + discount.toPlainString() + "%");
        }
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
