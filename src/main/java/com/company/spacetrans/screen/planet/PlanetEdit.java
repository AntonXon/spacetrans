package com.company.spacetrans.screen.planet;

import io.jmix.ui.screen.*;
import com.company.spacetrans.entity.Planet;

@UiController("st_Planet.edit")
@UiDescriptor("planet-edit.xml")
@EditedEntityContainer("planetDc")
public class PlanetEdit extends StandardEditor<Planet> {
}
