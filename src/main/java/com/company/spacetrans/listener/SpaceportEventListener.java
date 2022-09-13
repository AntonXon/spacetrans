package com.company.spacetrans.listener;

import com.company.spacetrans.entity.Spaceport;
import io.jmix.core.DataManager;
import io.jmix.core.SaveContext;
import io.jmix.core.event.EntityChangedEvent;
import io.jmix.core.event.EntitySavingEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component("st_SpaceportEventListener")
public class SpaceportEventListener {
    @Autowired
    private DataManager dataManager;

    @EventListener
    public void onSpaceportChangedBeforeCommit(EntityChangedEvent<Spaceport> event) {
        event.getChanges().getAttributes();
        if (event.getChanges().isChanged("isDefault")
                && (event.getChanges().getOldValue("isDefault") == null
                || Objects.equals(event.getChanges().getOldValue("isDefault"), false))) {
        }
    }

    @EventListener
    public void onSpaceportChangedOnSave(EntitySavingEvent<Spaceport> event) {
        Spaceport spaceport = event.getEntity();
        if (spaceport.getIsDefault()) {
            List<Spaceport> spaceports = dataManager.load(Spaceport.class)
                    .query("select s from st_Spaceport s " +
                            "where s.isDefault = true and s.moon = :moon and s.planet = :planet")
                    .parameter("moon", spaceport.getMoon())
                    .parameter("planet", spaceport.getPlanet())
                    .list();
            if (spaceports.size() == 0) {
                return;
            }
            spaceports.forEach(s -> s.setIsDefault(false));
            dataManager.save(spaceports.toArray());
        }
    }

}
