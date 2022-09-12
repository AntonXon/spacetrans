package com.company.spacetrans.app.service;

import com.company.spacetrans.entity.Planet;
import io.jmix.core.DataManager;
import io.jmix.core.FileRef;
import io.jmix.core.FileStorage;
import io.jmix.core.SaveContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;


@Component("st_PlanetImportService")
public class PlanetImportService {
    public final int NAME_COL = 0;
    public final int MASS_COL = 1;
    public final int AXIS_COL = 2;
    public final int ORBITAL_PERIOD_COL = 3;
    public final int ROTATION_PERIOD_COL = 4;
    public final int RINGS_COL = 5;

    @Autowired
    private FileStorage fileStorage;
    @Autowired
    private DataManager dataManager;

    public void importPlanetsFromFile(FileRef fileRef) {
        InputStream is = fileStorage.openStream(fileRef);
        List<String> lines = new BufferedReader(new InputStreamReader(is)).lines().parallel().collect(Collectors.toList());
        SaveContext saveContext = new SaveContext();
        //process lines
        for (int rowInd = 1; rowInd < lines.size(); rowInd++) {
            //split by comma
            String[] attrValues = lines.get(rowInd).split(",");
            String name = attrValues[NAME_COL].trim();
            //create or update entity
            Planet planet = getPlanetByName(name);
            //set attributes
            planet.setMass(Double.valueOf(attrValues[MASS_COL].trim()));
            planet.setSemiMajorAxis(Double.valueOf(attrValues[AXIS_COL].trim()));
            planet.setOrbitalPeriod(Double.valueOf(attrValues[ORBITAL_PERIOD_COL].trim()));
            planet.setRotationPeriod(Double.valueOf(attrValues[ROTATION_PERIOD_COL].trim()));
            planet.setRings(attrValues[RINGS_COL].trim().equalsIgnoreCase("yes"));
            saveContext.saving(planet);
        }
        //save all
        dataManager.save(saveContext);
        //remove file
        fileStorage.removeFile(fileRef);
    }

    public Planet getPlanetByName(String name) {
        Planet planet = dataManager.load(Planet.class)
                .query("select p from st_Planet p where p.name = :name")
                .parameter("name", name)
                .optional()
                .orElse(null);
        if (planet == null) {
            planet = dataManager.create(Planet.class);
            planet.setName(name);
        }
        return planet;
    }
}
