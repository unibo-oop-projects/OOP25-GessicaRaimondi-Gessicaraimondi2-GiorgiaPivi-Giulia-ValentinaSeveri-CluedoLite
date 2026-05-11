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
import it.unibo.CluedoLite.controller.buttonflowcontroller.api.ResetButtonController;
import it.unibo.CluedoLite.controller.buttonflowcontroller.api.QuitButtonController;
import it.unibo.CluedoLite.controller.accuseandsuspectcontroller.api.InterfaceSuspicionController;
import it.unibo.CluedoLite.controller.accuseandsuspectcontroller.api.InterfaceAccusation;

/**
 * Panel containing game action buttons.
 * SUSPICION and ACCUSATION buttons are at the top,
 * RESET and QUIT buttons are at the bottom.
 *
 * All controllers are injected externally — this panel handles layout only.
 */
public class ButtonGamePanel extends JPanel {

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
                           final QuitButtonController quitController) {

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

        final ButtonSuspicionView suspicionButton = new ButtonSuspicionView(suspicionController);
        suspicionButton.setBackground(AppColorFont.BACKGROUND_MEDIUM);
        suspicionButton.setMaximumSize(new Dimension(300, 55));
        suspicionButton.setPreferredSize(new Dimension(300, 55));

        final ButtonAccuseView accuseButton = new ButtonAccuseView(accuseController);
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
        final ResetButtonView resetButton = new ResetButtonView(resetController);
        resetButton.setMaximumSize(new Dimension(150, 40));
        resetButton.setPreferredSize(new Dimension(150, 40));
        resetButton.setFont(AppColorFont.FONT_BUTTON.deriveFont(13f));

        final QuitButtonView quitButton = new QuitButtonView(quitController);
        quitButton.setMaximumSize(new Dimension(150, 40));
        quitButton.setPreferredSize(new Dimension(150, 40));
        quitButton.setFont(AppColorFont.FONT_BUTTON.deriveFont(13f));

        add(resetButton);
        add(Box.createVerticalStrut(10));
        add(quitButton);
    }
}