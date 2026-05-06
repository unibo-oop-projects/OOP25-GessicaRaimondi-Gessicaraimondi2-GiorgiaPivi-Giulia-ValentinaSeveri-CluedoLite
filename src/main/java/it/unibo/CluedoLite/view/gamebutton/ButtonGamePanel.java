package it.unibo.CluedoLite.view.gamebutton;

import javax.swing.JPanel;
import javax.swing.BoxLayout;

import java.awt.Dimension;
import java.awt.Component;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.Box;
import it.unibo.CluedoLite.view.AppColorFont;
import it.unibo.CluedoLite.view.buttonflowview.QuitButtonView;
import it.unibo.CluedoLite.view.buttonflowview.ResetButtonView;
import it.unibo.CluedoLite.view.suspicionview.ButtonSuspicionView;
import it.unibo.CluedoLite.view.accuseview.ButtonAccuseView;
import it.unibo.CluedoLite.controller.buttonflowcontroller.impl.ResetButtonControllerImpl;
import it.unibo.CluedoLite.controller.buttonflowcontroller.impl.QuitButtonControllerImpl;
import it.unibo.CluedoLite.model.gameflow.api.Game;

/**
 * Panel containing game action buttons.
 * SUSPICION and ACCUSATION buttons are at the top,
 * RESET and QUIT buttons are at the bottom.
 *
 * The suspicion and accusation views are built externally
 * and injected here — this panel handles layout only.
 */
public class ButtonGamePanel extends JPanel {

    /**
     * Creates the panel with action buttons at top and flow buttons at bottom.
     *
     * @param game            the game model
     * @param suspicionButton the already-built suspicion button view
     * @param accuseButton    the already-built accuse button view
     */
    public ButtonGamePanel(final Game game,
                           final ButtonSuspicionView suspicionButton,
                           final ButtonAccuseView accuseButton) {

        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int panelWidth = screen.width / 4;
        final int panelHeight = screen.height;
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(AppColorFont.BACKGROUND_MEDIUM);

        // --- bottoni in alto ---
        final JPanel topButtons = new JPanel();
        topButtons.setLayout(new BoxLayout(topButtons, BoxLayout.Y_AXIS));
        topButtons.setBackground(AppColorFont.BACKGROUND_MEDIUM);
        topButtons.setAlignmentX(Component.CENTER_ALIGNMENT);

        suspicionButton.setBackground(AppColorFont.BACKGROUND_MEDIUM);
        suspicionButton.setMaximumSize(new Dimension(300, 55));
        suspicionButton.setPreferredSize(new Dimension(300, 55));

        accuseButton.setBackground(AppColorFont.BACKGROUND_MEDIUM);
        accuseButton.setMaximumSize(new Dimension(300, 55));
        accuseButton.setPreferredSize(new Dimension(300, 55));

        topButtons.add(suspicionButton);
        topButtons.add(Box.createVerticalStrut(5));
        topButtons.add(accuseButton);
        topButtons.setBorder(BorderFactory.createEmptyBorder(10, 15, 0, 0));
        add(topButtons);

        // spazio flessibile che spinge i bottoni in basso
        add(Box.createVerticalGlue());

        // --- bottoni in basso ---
        final ResetButtonView resetButton = new ResetButtonView(new ResetButtonControllerImpl(game));
        resetButton.setMaximumSize(new Dimension(150, 40));
        resetButton.setPreferredSize(new Dimension(150, 40));
        resetButton.setFont(AppColorFont.FONT_BUTTON.deriveFont(13f));

        final QuitButtonView quitButton = new QuitButtonView(new QuitButtonControllerImpl(game));
        quitButton.setMaximumSize(new Dimension(150, 40));
        quitButton.setPreferredSize(new Dimension(150, 40));
        quitButton.setFont(AppColorFont.FONT_BUTTON.deriveFont(13f));

        add(resetButton);
        add(Box.createVerticalStrut(10));
        add(quitButton);
    }
}