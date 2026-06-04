package it.unibo.cluedolite.controller.buttonflowcontroller.impl;

import java.util.function.Supplier;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.cluedolite.controller.buttonflowcontroller.api.QuitButtonController;
import it.unibo.cluedolite.controller.menucontroller.impl.StartControllerImpl;
import it.unibo.cluedolite.model.gameflow.api.Game;
import it.unibo.cluedolite.view.menuview.StartView;

/**
 * Implementation of {@link QuitButtonController} that handles the quit action.
 * When triggered, it shows a confirmation dialog and, if confirmed,
 * returns to the main menu by disposing the current game window.
 */
public class QuitButtonControllerImpl implements QuitButtonController {

    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP2",
        justification = "Intentional: controller must mutate game state"
    )
    private final Game game;
    private final Supplier<JFrame> frameSupplier;

    /**
     * Constructs a new {@code QuitButtonControllerImpl}.
     *
     * @param game          the game model used to invoke quit logic
     * @param frameSupplier a supplier providing the current game {@link JFrame}
     */
    public QuitButtonControllerImpl(final Game game, final Supplier<JFrame> frameSupplier) {
        this.game = game;
        this.frameSupplier = frameSupplier;
    }

    /**
     * Handles the quit button click event.
     * Displays a YES/NO confirmation dialog; if confirmed, calls
     * {@link Game#quitToMenu()}, opens the start view, and disposes the frame.
     */
    @Override
    public void onQuitClicked() {
        final JFrame currentFrame = frameSupplier.get();
        final int confirm = JOptionPane.showConfirmDialog(
                currentFrame,
                "Are you sure you want to quit to the main menu?",
                "Quit",
                JOptionPane.YES_NO_OPTION
        );
        if (confirm == JOptionPane.YES_OPTION) {
            game.quitToMenu();
            new StartView(new StartControllerImpl());
            currentFrame.dispose();
        }
    }
}
