package it.unibo.cluedolite.controller.buttonflowcontroller.api;

/**
 * Defines the contract for the QuitButton controller
 */
public interface QuitButtonController {

    /**
     * Called when the user clicks QUIT
     * Asks for confirmation before returning to the main menu
     */
    void onQuitClicked();
}