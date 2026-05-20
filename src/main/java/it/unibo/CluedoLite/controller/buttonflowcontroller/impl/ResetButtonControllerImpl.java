package it.unibo.CluedoLite.controller.buttonflowcontroller.impl;

import javax.swing.JOptionPane;
import it.unibo.CluedoLite.controller.buttonflowcontroller.api.ResetButtonController;
import it.unibo.CluedoLite.model.gameflow.api.Game;


/**
 * Controller for the RESET button
 * Restarts the game with the same players
 */
public class ResetButtonControllerImpl implements ResetButtonController {

    private final Game game;

    /**
     * Creates the controller with the game model
     */
    public ResetButtonControllerImpl(final Game game) {
        this.game = game;
    }

    /**
     * Called when the user clicks RESET
     */
    @Override
    public boolean onResetClicked() {
        final int confirm = JOptionPane.showConfirmDialog(
            null,
            "Are you sure you want to restart?",
            "Reset",
            JOptionPane.YES_NO_OPTION
        );
        if (confirm == JOptionPane.YES_OPTION){   
            game.resetGame();
            game.startGame();
            return true;
        }
        return false;
    }
}