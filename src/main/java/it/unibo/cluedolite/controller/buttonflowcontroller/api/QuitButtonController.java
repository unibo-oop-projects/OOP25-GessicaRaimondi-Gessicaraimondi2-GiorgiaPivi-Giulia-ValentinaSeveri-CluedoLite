package it.unibo.cluedolite.controller.buttonflowcontroller.api;

/**
 * Defines the contract for the quit button controller.
 * Implementations are responsible for handling the user's request
 * to exit the current game and return to the main menu.
 */
@FunctionalInterface
public interface QuitButtonController {

    /**
     * Handles the quit button click event.
     * Typically shows a confirmation dialog before performing the quit action.
     */
    void onQuitClicked();
}
