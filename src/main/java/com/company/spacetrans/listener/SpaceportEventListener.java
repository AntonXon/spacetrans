package com.company.spacetrans.listener;

import com.company.spacetrans.entity.Spaceport;
import io.jmix.core.event.EntityChangedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component("st_SpaceportEventListener")
public class SpaceportEventListener {

    @EventListener
    public void onSpaceportChangedBeforeCommit(EntityChangedEvent<Spaceport> event) {
        //event.getChanges().isChanged("")
    }
}
