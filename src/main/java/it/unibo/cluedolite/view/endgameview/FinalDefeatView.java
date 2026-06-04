package it.unibo.cluedolite.view.endgameview;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;

import it.unibo.cluedolite.controller.buttonflowcontroller.api.QuitButtonController;
import it.unibo.cluedolite.controller.buttonflowcontroller.api.ResetButtonController;
import it.unibo.cluedolite.view.AppColorFont;
import it.unibo.cluedolite.view.buttonflowview.QuitButtonView;
import it.unibo.cluedolite.view.buttonflowview.ResetButtonView;

/**
 * View displayed when all players have been eliminated in CluedoLite.
 * Shows a fullscreen defeat message with reset and quit buttons,
 * allowing the player to start a new game or exit the application.
 */
public class FinalDefeatView extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final int BORDER_THICKNESS = 6;
    private static final float TITLE_FONT_SIZE = 72f;
    private static final int INNER_PADDING_V = 30;
    private static final int INNER_PADDING_H = 40;
    private static final int SUBTITLE_TOP_PADDING = 20;
    private static final int BUTTON_GAP_H = 20;
    private static final int BUTTON_GAP_V = 10;

    /**
     * Constructs and displays the final defeat screen.
     * 
     * @param resetController the controller handling the reset button action
     * @param quitController  the controller handling the quit button action
     */
    @SuppressWarnings("PMD.ConstructorCallsOverridableMethod")
    public FinalDefeatView(final ResetButtonController resetController,
                           final QuitButtonController quitController) {
        setTitle("Defeat");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        setUndecorated(true);

        final JPanel rootPanel = new JPanel(new BorderLayout());
        rootPanel.setBackground(AppColorFont.BACKGROUND_DARK);
        rootPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, BORDER_THICKNESS));

        final JPanel outerPanel = new JPanel(new GridBagLayout());
        outerPanel.setBackground(AppColorFont.BACKGROUND_DARK);

        final JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
        innerPanel.setBackground(AppColorFont.BACKGROUND_DARK);
        innerPanel.setBorder(BorderFactory.createEmptyBorder(
            INNER_PADDING_V, INNER_PADDING_H, INNER_PADDING_V, INNER_PADDING_H));

        final JLabel titleLabel = new JLabel("LOSER :(");
        titleLabel.setAlignmentX(CENTER_ALIGNMENT);
        titleLabel.setFont(AppColorFont.FONT_TITLE.deriveFont(TITLE_FONT_SIZE));
        titleLabel.setForeground(Color.BLACK);

        final JLabel subtitleLabel = new JLabel("Nobody won...");
        subtitleLabel.setAlignmentX(CENTER_ALIGNMENT);
        subtitleLabel.setFont(AppColorFont.FONT_LABEL);
        subtitleLabel.setForeground(AppColorFont.TEXT_SECONDARY);
        subtitleLabel.setBorder(BorderFactory.createEmptyBorder(SUBTITLE_TOP_PADDING, 0, 0, 0));

        innerPanel.add(titleLabel);
        innerPanel.add(subtitleLabel);
        outerPanel.add(innerPanel);

        final JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, BUTTON_GAP_H, BUTTON_GAP_V));
        buttonsPanel.setBackground(AppColorFont.BACKGROUND_DARK);

        final ResetButtonView resetBtn = new ResetButtonView(resetController);
        resetBtn.addActionListener(e -> {
            if (resetController.onResetClicked()) {
                dispose();
            }
        });
        buttonsPanel.add(resetBtn);
        buttonsPanel.add(new QuitButtonView(quitController));

        rootPanel.add(outerPanel, BorderLayout.CENTER);
        rootPanel.add(buttonsPanel, BorderLayout.SOUTH);

        add(rootPanel);
        setVisible(true);
    }
}
