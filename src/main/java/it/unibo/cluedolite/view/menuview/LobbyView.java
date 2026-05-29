package it.unibo.cluedolite.view.menuview;

import javax.swing.*;

import it.unibo.cluedolite.controller.menucontroller.api.LobbyController;
import it.unibo.cluedolite.view.AppColorFont;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/*
 * Lobby screen where players select their characters before the game starts.
 * Shows a dropdown for the number of players, one row per player with a character
 * dropdown, and a START PLAY button.
 */
public class LobbyView extends JFrame {

    private JComboBox<Integer> numPlayersBox; // menu for the number of player
    private JPanel playersPanel;
    private List<JComboBox<String>> characterBoxes; //list of player 

    private static final String[] CHARACTERS = {
        "Miss Scarlet [Red]",
        "Colonel Mustard [Yellow]",
        "Mrs. White [White]",
        "Mr. Green [Green]",
        "Mrs. Peacock [Blue]",
        "Professor Plum [Purple]",
    };

    /**
     * Creates and displays the lobby screen.
     *
     * @param controller the controller notified when START PLAY is clicked
     */
    public LobbyView(final LobbyController controller) {
        setTitle("Cluedo Lite - Lobby");
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(AppColorFont.BACKGROUND_MEDIUM);

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        characterBoxes = new ArrayList<>();

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 20, 10, 20);

        // Numero giocatori
        JPanel numPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        numPanel.setBackground(AppColorFont.BACKGROUND_MEDIUM);
        JLabel numLabel = new JLabel("Select Players:");
        numLabel.setFont(AppColorFont.FONT_LABEL);
        numLabel.setForeground(AppColorFont.TEXT_PRIMARY);
        numPlayersBox = new JComboBox<>(new Integer[]{3, 4, 5, 6});
        numPlayersBox.setBackground(AppColorFont.DROPDOWN_BACKGROUND);
        numPlayersBox.setForeground(AppColorFont.DROPDOWN_FOREGROUND);
        numPlayersBox.setFont(AppColorFont.FONT_DROPDOWN);
        numPanel.add(numLabel);
        numPanel.add(numPlayersBox);
        gbc.gridy = 1;
        add(numPanel, gbc);

        // Pannello giocatori
        playersPanel = new JPanel();
        playersPanel.setLayout(new BoxLayout(playersPanel, BoxLayout.Y_AXIS));
        playersPanel.setBackground(AppColorFont.BACKGROUND_MEDIUM);
        gbc.gridy = 2;
        add(playersPanel, gbc);

        // Bottone START PLAY
        final JButton StartButton = new JButton("START PLAY");
        StartButton.setFont(AppColorFont.FONT_BUTTON);
        StartButton.setBackground(AppColorFont.BUTTON_BACKGROUND);
        StartButton.setForeground(AppColorFont.BUTTON_FOREGROUND);
        StartButton.setPreferredSize(new Dimension(300, 60));
        StartButton.setFocusPainted(false);
        StartButton.setBorderPainted(false);
        StartButton.addActionListener(e -> controller.onPlayClicked(this));
        gbc.gridy = 3;
        gbc.insets = new Insets(20, 20, 20, 20);
        add(StartButton, gbc);

        // Aggiorna righe quando cambia il numero
        numPlayersBox.addActionListener(e -> updatePlayersPanel());

        
        updatePlayersPanel();

        setVisible(true);
    }

    /*
     * Updates the players panel based on the selected number of players.
     */
    private void updatePlayersPanel() {
        playersPanel.removeAll();
        characterBoxes.clear();

        int num = (int) numPlayersBox.getSelectedItem();

        for (int i = 0; i < num; i++) {
            JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
            row.setBackground(AppColorFont.BACKGROUND_DARK);
            row.setBorder(BorderFactory.createLineBorder(AppColorFont.BORDER, 1));

            JLabel playerLabel = new JLabel("Player " + (i + 1));
            playerLabel.setFont(AppColorFont.FONT_BODY);
            playerLabel.setForeground(AppColorFont.TEXT_PRIMARY);
            playerLabel.setPreferredSize(new Dimension(70, 20));

            JComboBox<String> characterBox = new JComboBox<>(CHARACTERS);
            characterBox.setBackground(AppColorFont.DROPDOWN_BACKGROUND);
            characterBox.setForeground(AppColorFont.DROPDOWN_FOREGROUND);
            characterBox.setFont(AppColorFont.FONT_DROPDOWN);

            row.add(playerLabel);
            row.add(characterBox);

            characterBoxes.add(characterBox);
            playersPanel.add(row);
        }

        playersPanel.revalidate();
        playersPanel.repaint();
    }

    /**
     * Returns the number of players currently selected.
     *
     * @return the selected player count (3–6)
     */
    public int getNumPlayers() {
        return (int) numPlayersBox.getSelectedItem();
    }

    /**
     * Returns the character name chosen by the player at the given index,
     * stripping the colour tag.
     *
     * @param index zero-based player index
     * @return the plain character name without the colour annotation
     */
    public String getSelectedCharacterName(final int index) {
        final String selected = (String) characterBoxes.get(index).getSelectedItem();
        return selected.split(" \\[")[0];
    }

    /**
     * Returns the number-of-players dropdown.
     *
     * @return the dropdown controlling the player count
     */
    public JComboBox<Integer> getNumPlayersBox() {
        return numPlayersBox;
    }
}