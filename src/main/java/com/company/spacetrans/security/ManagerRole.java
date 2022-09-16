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

@ResourceRole(name = "ManagerRole", code = "manager-role", scope = "UI")
public interface ManagerRole {
    @MenuPolicy(menuIds = {"st_Gas.browse", "st_Atmosphere.browse", "st_AtmosphericGas.browse", "st_Moon.browse", "st_Planet.browse", "st_Spaceport.browse", "st_Carrier.browse", "st_Discounts.browse", "st_Company.browse", "st_Individual.browse", "st_Waybill.browse"})
    @ScreenPolicy(screenIds = {"st_Gas.browse", "st_Atmosphere.browse", "st_AtmosphericGas.browse", "st_Moon.browse", "st_Planet.browse", "st_Spaceport.browse", "st_Carrier.browse", "st_Discounts.browse", "st_Company.browse", "st_Individual.browse", "st_Waybill.browse", "st_Atmosphere.edit", "st_AtmosphericGas.edit", "st_Carrier.edit", "st_Company.edit", "st_Gas.edit", "st_Individual.edit", "st_Moon.edit", "st_Planet.edit", "st_Spaceport.edit", "st_Waybill.edit", "st_WaybillItem.edit", "ui_AddConditionScreen", "ui_GroupFilterCondition.edit"})
    void screens();

    @EntityAttributePolicy(entityClass = AstronomicalBody.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = AstronomicalBody.class, actions = EntityPolicyAction.ALL)
    void astronomicalBody();

    @EntityAttributePolicy(entityClass = Atmosphere.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Atmosphere.class, actions = EntityPolicyAction.ALL)
    void atmosphere();

    @EntityAttributePolicy(entityClass = AtmosphericGas.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = AtmosphericGas.class, actions = EntityPolicyAction.ALL)
    void atmosphericGas();

    @EntityAttributePolicy(entityClass = Carrier.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Carrier.class, actions = EntityPolicyAction.ALL)
    void carrier();

    @EntityAttributePolicy(entityClass = Company.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Company.class, actions = EntityPolicyAction.ALL)
    void company();

    @EntityAttributePolicy(entityClass = Coordinates.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Coordinates.class, actions = EntityPolicyAction.ALL)
    void coordinates();

    @EntityAttributePolicy(entityClass = Customer.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Customer.class, actions = EntityPolicyAction.ALL)
    void customer();

    @EntityAttributePolicy(entityClass = Dimensions.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Dimensions.class, actions = EntityPolicyAction.ALL)
    void dimensions();

    @EntityAttributePolicy(entityClass = Discounts.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Discounts.class, actions = EntityPolicyAction.ALL)
    void discounts();

    @EntityAttributePolicy(entityClass = Gas.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Gas.class, actions = EntityPolicyAction.ALL)
    void gas();

    @EntityAttributePolicy(entityClass = Individual.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Individual.class, actions = EntityPolicyAction.ALL)
    void individual();

    @EntityAttributePolicy(entityClass = Moon.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Moon.class, actions = EntityPolicyAction.ALL)
    void moon();

    @EntityAttributePolicy(entityClass = Planet.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Planet.class, actions = EntityPolicyAction.ALL)
    void planet();

    @EntityAttributePolicy(entityClass = Spaceport.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Spaceport.class, actions = EntityPolicyAction.ALL)
    void spaceport();

    @EntityAttributePolicy(entityClass = Waybill.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Waybill.class, actions = EntityPolicyAction.ALL)
    void waybill();

    @EntityAttributePolicy(entityClass = WaybillItem.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = WaybillItem.class, actions = EntityPolicyAction.ALL)
    void waybillItem();

    @EntityAttributePolicy(entityClass = User.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = User.class, actions = EntityPolicyAction.READ)
    void user();

    @SpecificPolicy(resources = "ui.filter.modifyConfiguration")
    void specific();
}
