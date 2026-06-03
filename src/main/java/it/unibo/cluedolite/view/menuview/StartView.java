package it.unibo.cluedolite.view.menuview;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import it.unibo.cluedolite.controller.menucontroller.api.StartController;
import it.unibo.cluedolite.view.AppColorFont;

/**
 * Main menu screen of CluedoLite.
 * This is the first screen the user sees when the game starts.
 * It displays the title, a rules button, a new game button, and player count info.
 */
public final class StartView extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final String FONT_NAME = "Serif";
    private static final int TITLE_FONT_SIZE = 60;
    private static final int BUTTON_WIDTH = 350;
    private static final int BUTTON_HEIGHT = 60;
    private static final int INSET_SMALL = 10;
    private static final int INSET_MEDIUM = 20;
    private static final int INSET_LARGE = 30;
    private static final int INSET_BUTTON = 15;

    private static final String RULES_TEXT =
        "CLUEDO LITE RULES:\n\n"
        + "OBJECTIVE:\n"
        + "Find the murderer, the weapon and the room\n\n"
        + "SETUP:\n"
        + "- 3 to 6 players, each choosing a unique character\n"
        + "- One character, one weapon and one room are randomly chosen\n"
        + "- Automatic creation of the secret solution\n"
        + "- Remaining cards are automatically dealt to players\n\n"
        + "TURN:\n"
        + "1. Move to an adjacent room on the board\n"
        + "2. Make a suspicion OR a final accusation\n"
        + "3. Click 'End Turn' to pass to the next player\n\n"
        + "SUSPICION:\n"
        + "- Only allowed in the room where you currently are\n"
        + "- Choose a character and a weapon\n"
        + "- The result is recorded in your suspect notes\n\n"
        + "ACCUSATION:\n"
        + "- Choose character, weapon AND room freely\n"
        + "- Correct: you win and the game ends for everyone!\n"
        + "- Wrong: you are eliminated but the game continues for others\n\n"
        + "SUSPECT NOTES:\n"
        + "- Each player has a personal notes table\n"
        + "- Your cards are marked automatically at the start\n"
        + "- Revealed cards are marked automatically during the game\n\n"
        + "NOTE:\n"
        + "You must make a suspicion or accusation before ending your turn.";

    private final JButton startButton;

    /**
     * Constructs and displays the main menu screen.
     *
     * @param controller the {@link StartController} notified when NEW GAME is clicked
     */
    public StartView(final StartController controller) {
        setTitle("Cluedo Lite");
        setLocationRelativeTo(null);
        getContentPane().setBackground(AppColorFont.BACKGROUND_MEDIUM);
        setLayout(new GridBagLayout());
        setExtendedState(MAXIMIZED_BOTH);

        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;

        final JLabel title = new JLabel("CLUEDO LITE", SwingConstants.CENTER);
        title.setFont(new Font(FONT_NAME, Font.BOLD, TITLE_FONT_SIZE));
        title.setForeground(AppColorFont.TEXT_PRIMARY);
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, INSET_SMALL, 0);
        add(title, gbc);

        final JButton rulesButton = new JButton("RULES");
        rulesButton.setFont(AppColorFont.FONT_BUTTON);
        rulesButton.setBackground(AppColorFont.BUTTON_BACKGROUND);
        rulesButton.setForeground(AppColorFont.BUTTON_FOREGROUND);
        rulesButton.setFocusPainted(false);
        rulesButton.setBorderPainted(false);
        rulesButton.addActionListener(e -> JOptionPane.showMessageDialog(
                null,
                RULES_TEXT,
                "Rules",
                JOptionPane.INFORMATION_MESSAGE
        ));
        gbc.gridy = 1;
        gbc.insets = new Insets(INSET_MEDIUM, 0, INSET_LARGE, 0);
        add(rulesButton, gbc);

        startButton = new JButton("NEW GAME");
        startButton.setFont(AppColorFont.FONT_BUTTON);
        startButton.setBackground(AppColorFont.BUTTON_BACKGROUND);
        startButton.setForeground(AppColorFont.BUTTON_FOREGROUND);
        startButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        startButton.setFocusPainted(false);
        startButton.setBorderPainted(false);
        startButton.addActionListener(e -> controller.onStartClicked(this));
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, INSET_BUTTON, 0);
        add(startButton, gbc);

        final JLabel players = new JLabel("3 - 6 players", SwingConstants.CENTER);
        players.setFont(AppColorFont.FONT_BODY);
        players.setForeground(AppColorFont.TEXT_PRIMARY);
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(players, gbc);

        setVisible(true);
    }

}
