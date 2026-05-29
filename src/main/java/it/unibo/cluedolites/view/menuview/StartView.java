package it.unibo.cluedolite.view.menuview;

import javax.swing.*;

import it.unibo.cluedolite.controller.menucontroller.api.StartController;
import it.unibo.cluedolite.view.AppColorFont;

import java.awt.*;
/*
 * Main menu screen
*  this is the first thing the user sees when the game starts
 * It shows thetitle, a button to start a new game,
 * and players info
 */
public class StartView extends JFrame {

    // The button that starts a new game
    private JButton startButton;

    //Creates and displays the main menu screen
    public StartView(final StartController controller) {
        setTitle("Cluedo Lite");
        setLocationRelativeTo(null); //center the window on screen
        getContentPane().setBackground(AppColorFont.BACKGROUND_MEDIUM);

        //Use to center components
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; //same column
        
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Title lable
        JLabel title = new JLabel("CLUEDO LITE", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 60));
        title.setForeground(AppColorFont.TEXT_PRIMARY);
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 10, 0);
        add(title, gbc);

        final JButton rulesButton = new JButton("RULES");
        rulesButton.setFont(AppColorFont.FONT_BUTTON);
        rulesButton.setBackground(AppColorFont.BUTTON_BACKGROUND);
        rulesButton.setForeground(AppColorFont.BUTTON_FOREGROUND);
        rulesButton.setFocusPainted(false);
        rulesButton.setBorderPainted(false);
       rulesButton.addActionListener(e -> JOptionPane.showMessageDialog(
            null,
            "CLUEDO LITE RULES:\n\n" +
            "OBJECTIVE:\n" +
            "Find the murderer, the weapon and the room\n\n" +
            "SETUP:\n" +
            "- 3 to 6 players, each choosing a unique character\n" +
            "- One character, one weapon and one room are randomly\n" +
            "- Automatic creation of the secret solution\n" +
            "- Remaining cards are automatically dealt to players\n\n" +
            "TURN:\n" +
            "1. Move to an adjacent room on the board\n" +
            "2. Make a suspicion OR a final accusation\n" +
            "3. Click 'End Turn' to pass to the next player\n\n" +
            "SUSPICION:\n" +
            "- Only allowed in the room where you currently are\n" +
            "- Choose a character and a weapon\n" +
            "- The result is recorded in your suspect notes\n\n" +
            "ACCUSATION:\n" +
            "- Choose character, weapon AND room freely\n" +
            "- Correct: you win and the game ends for everyone!\n" +
            "- Wrong: you are eliminated but the game continues for others\n" +
            "- If you are the last player remaining, you must make an accusation:\n" +
            "  correct → you win, wrong → everyone loses!\n\n" +
            "SUSPECT NOTES:\n" +
            "- Each player has a personal notes table\n" +
            "- Your cards are marked automatically at the start\n" +
            "- Revealed cards are marked automatically during the game\n\n" +
            "NOTE:\n" +
            "You must make a suspicion or accusation before ending your turn.",
            "Rules",
            JOptionPane.INFORMATION_MESSAGE
        ));
        gbc.gridy = 1;
        gbc.insets = new Insets(20, 0, 30, 0);
        add(rulesButton, gbc);

        // NEW GAME Button 
        startButton = new JButton("NEW GAME");
        startButton.setFont(AppColorFont.FONT_BUTTON);
        startButton.setBackground(AppColorFont.BUTTON_BACKGROUND);
        startButton.setForeground(AppColorFont.BUTTON_FOREGROUND);
        startButton.setPreferredSize(new Dimension(350, 60));
        startButton.setFocusPainted(false);
        startButton.setBorderPainted(false);
        startButton.addActionListener(e -> controller.onStartClicked(this));
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 15, 0);
        add(startButton, gbc);

        //Label showing the number of players allowed
        JLabel players = new JLabel("3 - 6 players", SwingConstants.CENTER);
        players.setFont(AppColorFont.FONT_BODY);
        players.setForeground(AppColorFont.TEXT_PRIMARY);
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 0, 0, 0);
        add(players, gbc);

        setVisible(true);
    }
}