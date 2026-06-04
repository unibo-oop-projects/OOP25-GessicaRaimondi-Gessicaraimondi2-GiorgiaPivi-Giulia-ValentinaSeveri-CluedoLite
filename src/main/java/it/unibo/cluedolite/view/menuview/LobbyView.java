package it.unibo.cluedolite.view.menuview;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.cluedolite.controller.menucontroller.api.LobbyController;
import it.unibo.cluedolite.view.AppColorFont;

/**
 * Lobby screen where players select their characters before the game starts.
 * Shows a dropdown for the number of players, one row per player with a character
 * dropdown, and a START PLAY button.
 */
public final class LobbyView extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final int BUTTON_WIDTH = 300;
    private static final int BUTTON_HEIGHT = 60;
    private static final int INSET_SMALL = 10;
    private static final int INSET_MEDIUM = 20;
    private static final int LABEL_WIDTH = 70;
    private static final int LABEL_HEIGHT = 20;
    private static final int MIN_PLAYERS = 3;
    private static final int MAX_PLAYERS = 6;

    private static final String[] CHARACTERS = {
        "Miss Scarlet [Red]",
        "Colonel Mustard [Yellow]",
        "Mrs. White [White]",
        "Mr. Green [Green]",
        "Mrs. Peacock [Blue]",
        "Professor Plum [Purple]",
    };

    private final JComboBox<Integer> numPlayersBox;
    private final JPanel playersPanel;
    private final List<JComboBox<String>> characterBoxes;

    /**
     * Constructs and displays the lobby screen.
     *
     * @param controller the {@link LobbyController} notified when START PLAY is clicked
     */
    public LobbyView(final LobbyController controller) {
        setTitle("Cluedo Lite - Lobby");
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(AppColorFont.BACKGROUND_MEDIUM);
        setExtendedState(MAXIMIZED_BOTH);

        characterBoxes = new ArrayList<>();
        playersPanel = new JPanel();

        setLayout(new GridBagLayout());
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(INSET_SMALL, INSET_MEDIUM, INSET_SMALL, INSET_MEDIUM);

        final JPanel numPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        numPanel.setBackground(AppColorFont.BACKGROUND_MEDIUM);
        final JLabel numLabel = new JLabel("Select Players:");
        numLabel.setFont(AppColorFont.FONT_LABEL);
        numLabel.setForeground(AppColorFont.TEXT_PRIMARY);
        numPlayersBox = new JComboBox<>(new Integer[]{MIN_PLAYERS, MIN_PLAYERS + 1, MAX_PLAYERS - 1, MAX_PLAYERS});
        numPlayersBox.setBackground(AppColorFont.DROPDOWN_BACKGROUND);
        numPlayersBox.setForeground(AppColorFont.DROPDOWN_FOREGROUND);
        numPlayersBox.setFont(AppColorFont.FONT_DROPDOWN);
        numPanel.add(numLabel);
        numPanel.add(numPlayersBox);
        gbc.gridy = 1;
        add(numPanel, gbc);

        playersPanel.setLayout(new BoxLayout(playersPanel, BoxLayout.Y_AXIS));
        playersPanel.setBackground(AppColorFont.BACKGROUND_MEDIUM);
        gbc.gridy = 2;
        add(playersPanel, gbc);

        final JButton startButton = new JButton("START PLAY");
        startButton.setFont(AppColorFont.FONT_BUTTON);
        startButton.setBackground(AppColorFont.BUTTON_BACKGROUND);
        startButton.setForeground(AppColorFont.BUTTON_FOREGROUND);
        startButton.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        startButton.setFocusPainted(false);
        startButton.setBorderPainted(false);
        startButton.addActionListener(e -> controller.onPlayClicked(this));
        gbc.gridy = 3;
        gbc.insets = new Insets(INSET_MEDIUM, INSET_MEDIUM, INSET_MEDIUM, INSET_MEDIUM);
        add(startButton, gbc);

        numPlayersBox.addActionListener(e -> updatePlayersPanel());
        updatePlayersPanel();

        setVisible(true);
    }

    /**
     * Updates the players panel based on the currently selected number of players.
     * Rebuilds all player rows and character dropdowns.
     */
    private void updatePlayersPanel() {
        playersPanel.removeAll();
        characterBoxes.clear();

        final int num = (int) numPlayersBox.getSelectedItem();

        for (int i = 0; i < num; i++) {
            final JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
            row.setBackground(AppColorFont.BACKGROUND_DARK);
            row.setBorder(BorderFactory.createLineBorder(AppColorFont.BORDER, 1));

            final JLabel playerLabel = new JLabel("Player " + (i + 1));
            playerLabel.setFont(AppColorFont.FONT_BODY);
            playerLabel.setForeground(AppColorFont.TEXT_PRIMARY);
            playerLabel.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));

            final JComboBox<String> characterBox = new JComboBox<>(CHARACTERS);
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
}
