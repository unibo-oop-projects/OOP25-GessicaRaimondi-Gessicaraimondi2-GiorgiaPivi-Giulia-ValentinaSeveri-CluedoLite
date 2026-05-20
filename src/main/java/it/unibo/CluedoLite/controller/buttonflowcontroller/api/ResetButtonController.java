package it.unibo.CluedoLite.controller.buttonflowcontroller.api;

/**
 * Defines the contract for the ResetButton controller
 */
public interface ResetButtonController {

    /**
     * Called when the user clicks RESET
     * Asks for confirmation before restarting the game with the same players
     */
    boolean onResetClicked();
}
