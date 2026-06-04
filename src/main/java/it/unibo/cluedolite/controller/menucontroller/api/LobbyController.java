package it.unibo.cluedolite.controller.menucontroller.api;

import it.unibo.cluedolite.view.menuview.LobbyView;

/**
 * Defines the contract for the lobby controller.
 * Manages the interactions of the lobby screen and handles
 * character assignment before the game starts.
 */
@SuppressWarnings("PMD.ImplicitFunctionalInterface")
public interface LobbyController {

    /**
     * Handles the play button click event.
     * Checks for duplicate character selections, assigns characters to players,
     * and starts the game.
     *
     * @param view the {@link LobbyView} from which player and character data are read
     */
    void onPlayClicked(LobbyView view);
}
