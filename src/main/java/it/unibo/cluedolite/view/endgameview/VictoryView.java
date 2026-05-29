package it.unibo.cluedolite.view.endgameview;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;

import it.unibo.cluedolite.controller.buttonflowcontroller.api.QuitButtonController;
import it.unibo.cluedolite.controller.buttonflowcontroller.api.ResetButtonController;
import it.unibo.cluedolite.view.AppColorFont;
import it.unibo.cluedolite.view.buttonflowview.QuitButtonView;
import it.unibo.cluedolite.view.buttonflowview.ResetButtonView;

/**
 * View displayed when a player wins the game in CluedoLite.
 * Shows a fullscreen victory message with reset and quit buttons,
 * allowing the player to start a new game or exit the application.
 */
public class VictoryView extends JFrame {

    private static final long serialVersionUID = 1L;
    
    private static final int WINDOW_WIDTH = 700;
    private static final int WINDOW_HEIGHT = 400;

    /**
    * Constructs and displays the victory screen.
    * @param resetController the controller handling the reset button action
    * @param quitController  the controller handling the quit button action
    */
    public VictoryView(final ResetButtonController resetController, final QuitButtonController quitController) {
        setTitle("Victory!");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);

        // Root panel: BorderLayout to place buttons at the bottom
        final JPanel rootPanel = new JPanel(new BorderLayout());
        rootPanel.setBackground(AppColorFont.BACKGROUND_DARK);
        rootPanel.setBorder(BorderFactory.createLineBorder(AppColorFont.ACCENT_SECONDARY, 6));

        // Outer panel centres content both vertically and horizontally
        final JPanel outerPanel = new JPanel(new GridBagLayout());
        outerPanel.setBackground(AppColorFont.BACKGROUND_DARK);

        // Inner vertical panel stacks the two text labels
        final JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
        innerPanel.setBackground(AppColorFont.BACKGROUND_DARK);
        innerPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        final JLabel titleLabel = new JLabel("WINNER :)");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(AppColorFont.FONT_TITLE.deriveFont(72f));
        titleLabel.setForeground(AppColorFont.ACCENT_SECONDARY);

        final JLabel subtitleLabel = new JLabel("Congratulations!");
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabel.setFont(AppColorFont.FONT_LABEL);
        subtitleLabel.setForeground(AppColorFont.TEXT_PRIMARY);
        subtitleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        innerPanel.add(titleLabel);
        innerPanel.add(subtitleLabel);
        outerPanel.add(innerPanel);

        // Buttons panel at the bottom
        final JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
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