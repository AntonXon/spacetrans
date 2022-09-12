package com.company.spacetrans.screen.planet;

import com.company.spacetrans.app.PlanetImportService;
import io.jmix.core.FileRef;
import io.jmix.ui.Dialogs;
import io.jmix.ui.Notifications;
import io.jmix.ui.UiComponents;
import io.jmix.ui.action.Action;
import io.jmix.ui.app.inputdialog.DialogOutcome;
import io.jmix.ui.app.inputdialog.InputParameter;
import io.jmix.ui.component.FileStorageUploadField;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import com.company.spacetrans.entity.Planet;
import io.jmix.ui.upload.TemporaryStorage;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.UUID;

@UiController("st_Planet.browse")
@UiDescriptor("planet-browse.xml")
@LookupComponent("planetsTable")
public class PlanetBrowse extends StandardLookup<Planet> {
    @Autowired
    private CollectionLoader<Planet> planetsDl;
    @Autowired
    private Dialogs dialogs;
    @Autowired
    private PlanetImportService planetImportService;
    @Autowired
    private Notifications notifications;

    @Subscribe("planetsTable.importData")
    public void onPlanetsTableImportData(Action.ActionPerformedEvent event) {
        dialogs.createInputDialog(this)
                .withCaption("Import Data")
                .withParameter(
                        InputParameter.fileParameter("file")
                                .withCaption("File with data")
                                .withRequired(true)
                )
                .withCloseListener(closeEvent -> {
                    if (closeEvent.closedWith(DialogOutcome.OK)) {
                        FileRef fileRef = closeEvent.getValue("file");
                        //start import
                        planetImportService.importPlanetsFromFile(fileRef);
                        //reload data
                        planetsDl.load();
                        //show notification
                        notifications
                                .create(Notifications.NotificationType.HUMANIZED)
                                .withCaption("Import completed")
                                .show();
                    }
                })
                .show();
    }

}
