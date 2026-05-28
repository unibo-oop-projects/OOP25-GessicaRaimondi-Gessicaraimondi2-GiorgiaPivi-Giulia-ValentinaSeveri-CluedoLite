package it.unibo.samplejavafx.app;

import javax.swing.SwingUtilities;

import it.unibo.CluedoLite.controller.menucontroller.impl.StartControllerImpl;
import it.unibo.CluedoLite.view.menuview.StartView;

/**
 * Entry point of CluedoLite.
 *
 * Launches the Swing UI on the Event Dispatch Thread and opens
 * the start screen.
 */
public final class Main {

    private Main() { }
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(() -> {
            final StartControllerImpl startController = new StartControllerImpl();
            final StartView startView = new StartView(startController);
            startView.setVisible(true);
        });
    }
}