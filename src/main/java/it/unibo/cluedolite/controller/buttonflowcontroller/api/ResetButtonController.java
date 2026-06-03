package it.unibo.cluedolite.controller.buttonflowcontroller.api;

/**
 * Defines the contract for the reset button controller.
 * Implementations are responsible for handling the user's request
 * to restart the current game while keeping the same players.
 */
@FunctionalInterface
public interface ResetButtonController {

    /**
     * Handles the reset button click event.
     * Typically shows a confirmation dialog before restarting the game.
     * 
     * @return {@code true} if the reset was confirmed and performed,
     *         {@code false} if the user cancelled the operation
     */
    boolean onResetClicked();
}
