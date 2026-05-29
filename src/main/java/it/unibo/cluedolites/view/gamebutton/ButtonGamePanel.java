package it.unibo.cluedolite.view.gamebutton;

import javax.swing.JPanel;

import it.unibo.cluedolite.controller.accuseandsuspectcontroller.api.InterfaceAccusation;
import it.unibo.cluedolite.controller.accuseandsuspectcontroller.api.InterfaceSuspicionController;
import it.unibo.cluedolite.controller.buttonflowcontroller.api.QuitButtonController;
import it.unibo.cluedolite.controller.buttonflowcontroller.api.ResetButtonController;
import it.unibo.cluedolite.controller.endturnbuttoncontroller.api.EndTurnController;
import it.unibo.cluedolite.view.AppColorFont;
import it.unibo.cluedolite.view.accuseview.ButtonAccuseView;
import it.unibo.cluedolite.view.buttonflowview.QuitButtonView;
import it.unibo.cluedolite.view.buttonflowview.ResetButtonView;
import it.unibo.cluedolite.view.endturnbuttonview.EndTurnButtonView;
import it.unibo.cluedolite.view.suspicionview.ButtonSuspicionView;

import javax.swing.BoxLayout;

import java.awt.Dimension;
import java.awt.Component;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.Box;

/**
 * Panel containing game action buttons.
 * SUSPICION and ACCUSATION buttons are at the top,
 * RESET and QUIT buttons are at the bottom.
 *
 * All controllers are injected externally — this panel handles layout only.
 */
public class ButtonGamePanel extends JPanel {

    private final ButtonSuspicionView suspicionButton;
    private final ButtonAccuseView    accuseButton;
    private final EndTurnButtonView   endTurnButton;
    private final HistoryPanel historyPanel;

    /**
     * Creates the panel with action buttons at top and flow buttons at bottom.
     *
     * @param suspicionController  the controller for the suspicion action
     * @param accuseController     the controller for the accusation action
     * @param resetController      the controller for the reset action
     * @param quitController       the controller for the quit action
     */
    public ButtonGamePanel(final InterfaceSuspicionController suspicionController,
                           final InterfaceAccusation accuseController,
                           final ResetButtonController resetController,
                           final QuitButtonController quitController,
                           final EndTurnController endTurnController) {

        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int panelWidth = screen.width / 5;
        final int panelHeight = screen.height;
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(AppColorFont.BACKGROUND_MEDIUM);

        // --- bottoni in alto ---
        final JPanel topButtons = new JPanel();
        topButtons.setLayout(new BoxLayout(topButtons, BoxLayout.Y_AXIS));
        topButtons.setBackground(AppColorFont.BACKGROUND_MEDIUM);
        topButtons.setAlignmentX(Component.CENTER_ALIGNMENT);

        suspicionButton = new ButtonSuspicionView(suspicionController);
        suspicionButton.setBackground(AppColorFont.BACKGROUND_MEDIUM);
        suspicionButton.setMaximumSize(new Dimension(300, 55));
        suspicionButton.setPreferredSize(new Dimension(300, 55));

        accuseButton = new ButtonAccuseView(accuseController);
        accuseButton.setBackground(AppColorFont.BACKGROUND_MEDIUM);
        accuseButton.setMaximumSize(new Dimension(300, 55));
        accuseButton.setPreferredSize(new Dimension(300, 55));

        topButtons.add(suspicionButton);
        topButtons.add(Box.createVerticalStrut(5));
        topButtons.add(accuseButton);
        topButtons.add(Box.createVerticalStrut(10));
        historyPanel = new HistoryPanel(panelWidth - 30);
        topButtons.add(historyPanel);
        topButtons.setBorder(BorderFactory.createEmptyBorder(10, 15, 0, 0));
        add(topButtons);

        // spazio flessibile che spinge i bottoni in basso
        add(Box.createVerticalGlue());

        // --- bottoni in basso ---
        endTurnButton = new EndTurnButtonView(endTurnController);
        endTurnButton.setMaximumSize(new Dimension(150, 40));
        endTurnButton.setPreferredSize(new Dimension(150, 40));
        endTurnButton.setFont(AppColorFont.FONT_BUTTON.deriveFont(13f));

        final ResetButtonView resetButton = new ResetButtonView(resetController);
        resetButton.setMaximumSize(new Dimension(150, 40));
        resetButton.setPreferredSize(new Dimension(150, 40));
        resetButton.setFont(AppColorFont.FONT_BUTTON.deriveFont(13f));

        final QuitButtonView quitButton = new QuitButtonView(quitController);
        quitButton.setMaximumSize(new Dimension(150, 40));
        quitButton.setPreferredSize(new Dimension(150, 40));
        quitButton.setFont(AppColorFont.FONT_BUTTON.deriveFont(13f));

        add(endTurnButton);
        add(Box.createVerticalStrut(10));
        add(resetButton);
        add(Box.createVerticalStrut(10));
        add(quitButton);
    }

    /**
     * Called after the player makes a suspicion or accusation.
     * Disables suspicion and accusation, enables end turn.
     */
    public void disableActionButtons() {
        suspicionButton.setEnabled(false);
        accuseButton.setEnabled(false);
        endTurnButton.setEnabled(true);
        repaint();
    }
 
    /**
     * Called at the start of each new turn.
     * Re-enables suspicion and accusation, disables end turn.
     */
    public void resetForNewTurn() {
        suspicionButton.setEnabled(true);
        accuseButton.setEnabled(true);
        endTurnButton.setEnabled(false);
        repaint();
    }

    public void addHistoryEntry(String message) {
        historyPanel.addEntry(message);
    }

}