package it.unibo.cluedolite.controller.tablecontroller.api;

import java.util.Optional;

import it.unibo.cluedolite.model.accuseandsuspect.impl.Suspicion;
import it.unibo.cluedolite.model.creationcards.impl.Card;
import it.unibo.cluedolite.view.tableview.TablePanel;

/**
 * Controller responsible for managing the suspect notes table.
 * It coordinates the model and the view during the suspicion phase.
 */
public interface TableController {

    /**
     * Handles a suspicion by updating the model and the view
     * if a card is shown to refute it.
     *
     * @param suspicion  the suspicion made by the current player
     * @param shownCard  the card shown to refute the suspicion, if any
     */
    void handleSuspicion(Suspicion suspicion, Optional<Card> shownCard);

    /**
     * Returns the {@link TablePanel} for the current player,
     * creating it if it does not exist yet.
     *
     * @return the {@link TablePanel} for the current player
     */
    TablePanel refreshForPlayer();

}