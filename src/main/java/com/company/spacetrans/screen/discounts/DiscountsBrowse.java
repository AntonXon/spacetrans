package com.company.spacetrans.screen.discounts;

import io.jmix.ui.screen.*;
import com.company.spacetrans.entity.Discounts;

@UiController("st_Discounts.browse")
@UiDescriptor("discounts-browse.xml")
@LookupComponent("table")
public class DiscountsBrowse extends MasterDetailScreen<Discounts> {
}
