package com.company.spacetrans.security;


import com.company.spacetrans.entity.Waybill;
import io.jmix.security.role.annotation.JpqlRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;

@RowLevelRole(name = "Restrict waybills by user", code = "restricted-waybills")
public interface WaybillRestrictRole {

    @JpqlRowLevelPolicy(entityClass = Waybill.class, where = "{E}.creator.id = :current_user_id")
    void restrictWaybillByCreator();
}
