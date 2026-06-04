package it.unibo.cluedolite.controller.buttonflowcontroller.impl;

import javax.swing.JOptionPane;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.cluedolite.controller.buttonflowcontroller.api.ResetButtonController;
import it.unibo.cluedolite.model.gameflow.api.Game;

/**
 * Implementation of {@link ResetButtonController} that handles the reset action.
 * When triggered, it shows a confirmation dialog and, if confirmed,
 * resets and restarts the game with the same set of players.
 */
public class ResetButtonControllerImpl implements ResetButtonController {

    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP2",
        justification = "Intentional: controller must mutate game state"
    )
    private final Game game;

    /**
     * Constructs a new {@code ResetButtonControllerImpl}.
     *
     * @param game the game model used to invoke reset and start logic
     */
    public ResetButtonControllerImpl(final Game game) {
        this.game = game;
    }

    /**
     * Handles the reset button click event.
     * Displays a YES/NO confirmation dialog; if confirmed, calls
     * {@link Game#resetGame()} followed by {@link Game#startGame()}.
     *
     * @return {@code true} if the user confirmed the reset, {@code false} otherwise
     */
    @Override
    public boolean onResetClicked() {
        final int confirm = JOptionPane.showConfirmDialog(
            null,
            "Are you sure you want to restart?",
            "Reset",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            game.resetGame();
            game.startGame();
            return true;
        }

        return false;
    }
}
