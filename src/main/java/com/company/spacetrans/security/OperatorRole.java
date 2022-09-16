package com.company.spacetrans.security;

import com.company.spacetrans.entity.*;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.security.role.annotation.SpecificPolicy;
import io.jmix.securityui.role.annotation.MenuPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;

@ResourceRole(name = "OperatorRole", code = "operator-role", scope = "UI")
public interface OperatorRole {
    @MenuPolicy(menuIds = {"st_Gas.browse", "st_Atmosphere.browse", "st_AtmosphericGas.browse", "st_Moon.browse", "st_Planet.browse", "st_Spaceport.browse", "st_Carrier.browse", "st_Discounts.browse", "st_Company.browse", "st_Individual.browse", "st_Waybill.browse"})
    @ScreenPolicy(screenIds = {"st_Gas.browse", "st_Atmosphere.browse", "st_AtmosphericGas.browse", "st_Moon.browse", "st_Planet.browse", "st_Spaceport.browse", "st_Carrier.browse", "st_Discounts.browse", "st_Company.browse", "st_Individual.browse", "st_Waybill.browse", "st_Atmosphere.edit", "st_AtmosphericGas.edit", "st_Carrier.edit", "st_Company.edit", "st_Gas.edit", "st_Individual.edit", "st_Moon.edit", "st_Planet.edit", "st_Spaceport.edit", "st_Waybill.edit", "st_WaybillItem.edit", "ui_AddConditionScreen", "ui_GroupFilterCondition.edit"})
    void screens();

    @EntityAttributePolicy(entityClass = AstronomicalBody.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = AstronomicalBody.class, actions = EntityPolicyAction.READ)
    void astronomicalBody();

    @EntityAttributePolicy(entityClass = Atmosphere.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Atmosphere.class, actions = EntityPolicyAction.READ)
    void atmosphere();

    @EntityAttributePolicy(entityClass = AtmosphericGas.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = AtmosphericGas.class, actions = EntityPolicyAction.READ)
    void atmosphericGas();

    @EntityAttributePolicy(entityClass = Carrier.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Carrier.class, actions = {EntityPolicyAction.CREATE, EntityPolicyAction.READ, EntityPolicyAction.UPDATE})
    void carrier();

    @EntityAttributePolicy(entityClass = Company.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Company.class, actions = {EntityPolicyAction.CREATE, EntityPolicyAction.READ, EntityPolicyAction.UPDATE})
    void company();

    @EntityAttributePolicy(entityClass = Coordinates.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Coordinates.class, actions = EntityPolicyAction.READ)
    void coordinates();

    @EntityAttributePolicy(entityClass = Customer.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Customer.class, actions = {EntityPolicyAction.CREATE, EntityPolicyAction.READ, EntityPolicyAction.UPDATE})
    void customer();

    @EntityAttributePolicy(entityClass = Dimensions.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Dimensions.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.CREATE, EntityPolicyAction.UPDATE})
    void dimensions();

    @EntityAttributePolicy(entityClass = Discounts.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Discounts.class, actions = EntityPolicyAction.READ)
    void discounts();

    @EntityAttributePolicy(entityClass = Gas.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Gas.class, actions = EntityPolicyAction.READ)
    void gas();

    @EntityAttributePolicy(entityClass = Individual.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Individual.class, actions = {EntityPolicyAction.CREATE, EntityPolicyAction.READ, EntityPolicyAction.UPDATE})
    void individual();

    @EntityAttributePolicy(entityClass = Moon.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Moon.class, actions = EntityPolicyAction.READ)
    void moon();

    @EntityAttributePolicy(entityClass = Planet.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Planet.class, actions = EntityPolicyAction.READ)
    void planet();

    @EntityAttributePolicy(entityClass = Spaceport.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Spaceport.class, actions = EntityPolicyAction.READ)
    void spaceport();

    @EntityAttributePolicy(entityClass = User.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = User.class, actions = EntityPolicyAction.READ)
    void user();

    @EntityAttributePolicy(entityClass = Waybill.class, attributes = {"creator", "totalCharge", "totalWeight"}, action = EntityAttributePolicyAction.VIEW)
    @EntityAttributePolicy(entityClass = Waybill.class, attributes = {"id", "reference", "shipper", "consignee", "departurePort", "destinationPort", "carrier", "items"}, action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Waybill.class, actions = {EntityPolicyAction.CREATE, EntityPolicyAction.READ, EntityPolicyAction.UPDATE})
    void waybill();

    @EntityPolicy(entityClass = WaybillItem.class, actions = EntityPolicyAction.ALL)
    @EntityAttributePolicy(entityClass = WaybillItem.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    void waybillItem();

    @SpecificPolicy(resources = "ui.filter.modifyConfiguration")
    void specific();
}
