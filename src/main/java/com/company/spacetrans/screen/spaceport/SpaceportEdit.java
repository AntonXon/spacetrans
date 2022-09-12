package com.company.spacetrans.screen.spaceport;

import io.jmix.ui.screen.*;
import com.company.spacetrans.entity.Spaceport;

@UiController("st_Spaceport.edit")
@UiDescriptor("spaceport-edit.xml")
@EditedEntityContainer("spaceportDc")
public class SpaceportEdit extends StandardEditor<Spaceport> {
}
