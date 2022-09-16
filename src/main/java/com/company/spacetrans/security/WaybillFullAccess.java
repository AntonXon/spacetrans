package com.company.spacetrans.security;


import com.company.spacetrans.entity.Waybill;
import io.jmix.security.model.RowLevelBiPredicate;
import io.jmix.security.model.RowLevelPolicyAction;
import io.jmix.security.role.annotation.PredicateRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;
import org.springframework.context.ApplicationContext;

@RowLevelRole(name = "Full waybill access", code = "waybill-full-access")
public interface WaybillFullAccess {

    @PredicateRowLevelPolicy(entityClass = Waybill.class,
            actions = {RowLevelPolicyAction.CREATE, RowLevelPolicyAction.DELETE, RowLevelPolicyAction.UPDATE, RowLevelPolicyAction.READ})
    default RowLevelBiPredicate<Waybill, ApplicationContext> giveFullAccess() {
        return (((waybill, context) -> true));
    }
}
