package it.unibo.cluedolite.controller.menucontroller.api;

import it.unibo.cluedolite.view.menuview.LobbyView;

/*
 * Defines the contract for the LobbyController
 * Manages the interactions of the lobby screen
 * and handles the character assignment before the game starts
 */
public interface LobbyController {
    
    /**
     * Called when the user clicks PLAY
     * Checks for duplicate characters, assigns them to players
     * and starts the game
     */
    void onPlayClicked(LobbyView view);

}
