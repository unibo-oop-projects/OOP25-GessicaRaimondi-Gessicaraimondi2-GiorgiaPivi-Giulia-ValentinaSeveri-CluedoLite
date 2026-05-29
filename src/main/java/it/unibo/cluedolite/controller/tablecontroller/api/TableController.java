package it.unibo.cluedolite.controller.tablecontroller.api;

import java.util.Optional;

import it.unibo.cluedolite.model.accuseandsuspect.impl.Suspicion;
import it.unibo.cluedolite.model.creationcards.impl.Card;
import it.unibo.cluedolite.view.tableview.TablePanel;

public interface TableController {

    /**
     * Handles a suspicion: checks if any player can refute it,
     * updates the model and the view accordingly.
     *
     * @param suspicion the suspicion made by the current player
     */
    void handleSuspicion(Suspicion suspicion, Optional<Card> shownCard);

    public TablePanel refreshForPlayer();

}

