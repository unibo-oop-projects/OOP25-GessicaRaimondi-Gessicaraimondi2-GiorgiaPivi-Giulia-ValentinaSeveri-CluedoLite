package it.unibo.cluedolite.controller.endturnbuttoncontroller.api;

/**
 * Controller for the end turn action.
 */
@FunctionalInterface
public interface EndTurnController {

    /**
     * Called when the player clicks the "Fine turno" button.
     */
    void onEndTurnClicked();
}
