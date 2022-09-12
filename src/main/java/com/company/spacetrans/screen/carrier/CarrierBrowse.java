package com.company.spacetrans.screen.carrier;

import io.jmix.ui.screen.*;
import com.company.spacetrans.entity.Carrier;

@UiController("st_Carrier.browse")
@UiDescriptor("carrier-browse.xml")
@LookupComponent("carriersTable")
public class CarrierBrowse extends StandardLookup<Carrier> {
}
