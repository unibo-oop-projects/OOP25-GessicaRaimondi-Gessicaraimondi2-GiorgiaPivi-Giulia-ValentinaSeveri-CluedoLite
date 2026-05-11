package it.unibo.CluedoLite.controller.buttonflowcontroller.impl;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import it.unibo.CluedoLite.controller.buttonflowcontroller.api.QuitButtonController;
import it.unibo.CluedoLite.model.gameflow.api.Game;
import it.unibo.CluedoLite.view.menuview.StartView;
import it.unibo.CluedoLite.controller.menucontroller.impl.StartControllerImpl;

/**
 * Controller for the QUIT button.
 * Returns to the main menu and closes the game window.
 */
public class QuitButtonControllerImpl implements QuitButtonController {

    private final Game game;
    private final JFrame gameFrame;

    /**
     * Creates the controller with the game model and the game window reference.
     */
    public QuitButtonControllerImpl(final Game game, final JFrame gameFrame) {
        this.game = game;
        this.gameFrame = gameFrame;
    }

    /**
     * Called when the user clicks QUIT.
     */
    @Override
    public void onQuitClicked() {
        final int confirm = JOptionPane.showConfirmDialog(
            null,
            "Are you sure you want to quit to the main menu?",
            "Quit",
            JOptionPane.YES_NO_OPTION
        );
        if (confirm == JOptionPane.YES_OPTION) {
            game.quitToMenu();
            gameFrame.dispose();
            final StartControllerImpl startController = new StartControllerImpl();
            new StartView(startController);
        }
    }
}