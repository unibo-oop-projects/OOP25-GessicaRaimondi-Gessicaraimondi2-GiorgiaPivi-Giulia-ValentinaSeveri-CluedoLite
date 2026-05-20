package it.unibo.CluedoLite.controller.tablecontroller.api;

import it.unibo.CluedoLite.model.accuseandsuspect.impl.Suspicion;
import it.unibo.CluedoLite.view.tableview.TablePanel;

public interface TableController {

    /**
     * Handles a suspicion: checks if any player can refute it,
     * updates the model and the view accordingly.
     *
     * @param suspicion the suspicion made by the current player
     */
    void handleSuspicion(Suspicion suspicion);

    public TablePanel refreshForPlayer();

}

