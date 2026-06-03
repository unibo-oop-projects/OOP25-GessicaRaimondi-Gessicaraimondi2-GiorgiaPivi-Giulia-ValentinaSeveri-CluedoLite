package it.unibo.cluedolite.view.gamebutton;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
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

/**
 * Panel containing all game action buttons.
 * The suspicion and accusation buttons are displayed at the top,
 * while the end turn, reset, and quit buttons are anchored at the bottom.
 * All controllers are injected externally; this panel is responsible for layout only.
 */
public final class ButtonGamePanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private static final int BUTTON_WIDTH = 300;
    private static final int BUTTON_HEIGHT = 55;
    private static final int SMALL_BUTTON_WIDTH = 150;
    private static final int SMALL_BUTTON_HEIGHT = 40;
    private static final float SMALL_FONT_SIZE = 13f;
    private static final int STRUT_SMALL = 5;
    private static final int HISTORY_PADDING = 30;
    private static final int BORDER_PADDING = 15;
    private static final int STRUT_MEDIUM = 10;

    private final ButtonSuspicionView suspicionButton;
    private final ButtonAccuseView accuseButton;
    private final EndTurnButtonView endTurnButton;
    private final HistoryPanel historyPanel;

    /**
     * Constructs the game button panel with all action and flow buttons.
     *
     * @param suspicionController the controller handling the suspicion action
     * @param accuseController    the controller handling the accusation action
     * @param resetController     the controller handling the reset action
     * @param quitController      the controller handling the quit action
     * @param endTurnController   the controller handling the end turn action
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

        final JPanel topButtons = new JPanel();
        topButtons.setLayout(new BoxLayout(topButtons, BoxLayout.Y_AXIS));
        topButtons.setBackground(AppColorFont.BACKGROUND_MEDIUM);
        topButtons.setAlignmentX(CENTER_ALIGNMENT);

        suspicionButton = new ButtonSuspicionView(suspicionController);
        suspicionButton.setBackground(AppColorFont.BACKGROUND_MEDIUM);
        suspicionButton.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        suspicionButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));

        accuseButton = new ButtonAccuseView(accuseController);
        accuseButton.setBackground(AppColorFont.BACKGROUND_MEDIUM);
        accuseButton.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        accuseButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));

        topButtons.add(suspicionButton);
        topButtons.add(Box.createVerticalStrut(STRUT_SMALL));
        topButtons.add(accuseButton);
        topButtons.add(Box.createVerticalStrut(STRUT_MEDIUM));
        historyPanel = new HistoryPanel(panelWidth - HISTORY_PADDING);
        topButtons.add(historyPanel);
        topButtons.setBorder(BorderFactory.createEmptyBorder(STRUT_MEDIUM, BORDER_PADDING, 0, 0));
        add(topButtons);

        add(Box.createVerticalGlue());

        endTurnButton = new EndTurnButtonView(endTurnController);
        endTurnButton.setMaximumSize(new Dimension(SMALL_BUTTON_WIDTH, SMALL_BUTTON_HEIGHT));
        endTurnButton.setPreferredSize(new Dimension(SMALL_BUTTON_WIDTH, SMALL_BUTTON_HEIGHT));
        endTurnButton.setFont(AppColorFont.FONT_BUTTON.deriveFont(SMALL_FONT_SIZE));

        final ResetButtonView resetButton = new ResetButtonView(resetController);
        resetButton.setMaximumSize(new Dimension(SMALL_BUTTON_WIDTH, SMALL_BUTTON_HEIGHT));
        resetButton.setPreferredSize(new Dimension(SMALL_BUTTON_WIDTH, SMALL_BUTTON_HEIGHT));
        resetButton.setFont(AppColorFont.FONT_BUTTON.deriveFont(SMALL_FONT_SIZE));

        final QuitButtonView quitButton = new QuitButtonView(quitController);
        quitButton.setMaximumSize(new Dimension(SMALL_BUTTON_WIDTH, SMALL_BUTTON_HEIGHT));
        quitButton.setPreferredSize(new Dimension(SMALL_BUTTON_WIDTH, SMALL_BUTTON_HEIGHT));
        quitButton.setFont(AppColorFont.FONT_BUTTON.deriveFont(SMALL_FONT_SIZE));

        add(endTurnButton);
        add(Box.createVerticalStrut(STRUT_MEDIUM));
        add(resetButton);
        add(Box.createVerticalStrut(STRUT_MEDIUM));
        add(quitButton);
    }

    /**
     * Disables the suspicion and accusation buttons and enables the end turn button.
     * Should be called after the current player has made a suspicion or accusation.
     */
    public void disableActionButtons() {
        suspicionButton.setEnabled(false);
        accuseButton.setEnabled(false);
        endTurnButton.setEnabled(true);
        repaint();
    }

    /**
     * Re-enables the suspicion and accusation buttons and disables the end turn button.
     * Should be called at the start of each new turn.
     */
    public void resetForNewTurn() {
        suspicionButton.setEnabled(true);
        accuseButton.setEnabled(true);
        endTurnButton.setEnabled(false);
        repaint();
    }

    /**
     * Adds a new entry to the history panel.
     *
     * @param message the message to display in the history log
     */
    public void addHistoryEntry(final String message) {
        historyPanel.addEntry(message);
    }

}
