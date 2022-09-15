package com.company.spacetrans.screen.carrier;

import com.company.spacetrans.entity.Spaceport;
import io.jmix.core.DataManager;
import io.jmix.core.LoadContext;
import io.jmix.ui.screen.*;
import com.company.spacetrans.entity.Carrier;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@UiController("st_Carrier.browse")
@UiDescriptor("carrier-browse.xml")
@LookupComponent("carriersTable")
public class CarrierBrowse extends StandardLookup<Carrier> {
}
