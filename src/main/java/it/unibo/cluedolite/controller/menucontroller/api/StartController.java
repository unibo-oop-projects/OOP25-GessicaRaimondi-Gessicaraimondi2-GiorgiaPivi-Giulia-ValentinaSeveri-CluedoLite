package it.unibo.cluedolite.controller.menucontroller.api;

import it.unibo.cluedolite.view.menuview.StartView;

/**
 * Defines the contract for the start screen controller.
 * Handles the interactions of the main menu screen.
 */
@SuppressWarnings("PMD.ImplicitFunctionalInterface")
public interface StartController {

    /**
     * Handles the new game button click event.
     * Closes the main menu screen and opens the lobby screen.
     *
     * @param view the {@link StartView} representing the main menu screen to close
     */
    void onStartClicked(StartView view);
}
