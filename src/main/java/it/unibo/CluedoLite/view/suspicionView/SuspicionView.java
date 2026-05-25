package it.unibo.CluedoLite.view.suspicionview;

import javax.swing.*;
import java.awt.*;
import it.unibo.CluedoLite.view.AppColorFont;
import it.unibo.CluedoLite.model.creationcards.impl.Card;

/**
 * This class represents the Swing VIEW for the suspicion phase of the CluedoLite game.
 *
 * Responsibilities:
 * - displays to the player:
 *      - the room they are currently in (not editable, determined by the game)
 *      - a dropdown list of selectable characters
 *      - a dropdown list of selectable weapons
 *  - exposes getter methods so the controller can read the player's choices
 *  - exposes the confirm button so the controller can attach the confirmation logic
 *
 * This class contains NO game logic: it only handles presentation and input collection.
 * It does not know what happens after the player confirms — that is the controller's responsibility.
 */
public class SuspicionView extends JFrame {

    private final JComboBox<Card> characterBox;
    private final JComboBox<Card> weaponBox;
    private final JTextField roomField;
    private JButton confirmButton;

    /**
     * Constructs the suspicion view and initializes all its components.
     *
     * @param characters array of {@link Card} objects representing the available characters
     * @param weapons    array of {@link Card} objects representing the available weapons
     * @param room       {@link Card} representing the room where the player is currently located
     */
    public SuspicionView(Card[] characters, Card[] weapons, Card room) {

        setTitle("Make Your Suspicion:");
        setSize(700, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBackground(AppColorFont.BACKGROUND_DARK);
        add(panel, BorderLayout.CENTER);

        // Row 1: character selection
        JLabel charLabel = new JLabel("Choose the Character:");
        charLabel.setForeground(AppColorFont.TEXT_PRIMARY);
        charLabel.setFont(AppColorFont.FONT_LABEL);
        panel.add(charLabel);
        characterBox = new JComboBox<>(characters);
        characterBox.setBackground(AppColorFont.DROPDOWN_BACKGROUND);
        characterBox.setForeground(AppColorFont.DROPDOWN_FOREGROUND);
        characterBox.setFont(AppColorFont.FONT_DROPDOWN);
        panel.add(characterBox);

        // Row 2: weapon selection
        JLabel weapLabel = new JLabel("Choose the Weapon:");
        weapLabel.setForeground(AppColorFont.TEXT_PRIMARY);
        weapLabel.setFont(AppColorFont.FONT_LABEL);
        panel.add(weapLabel);
        weaponBox = new JComboBox<>(weapons);
        weaponBox.setBackground(AppColorFont.DROPDOWN_BACKGROUND);
        weaponBox.setForeground(AppColorFont.DROPDOWN_FOREGROUND);
        weaponBox.setFont(AppColorFont.FONT_DROPDOWN);
        panel.add(weaponBox);

        // Row 3: room display (read-only)
        JLabel roomLabel = new JLabel("The Room is:");
        roomLabel.setForeground(AppColorFont.TEXT_PRIMARY);
        roomLabel.setFont(AppColorFont.FONT_LABEL);
        panel.add(roomLabel);
        roomField = new JTextField(room.getName());
        roomField.setEditable(false);
        roomField.setBackground(AppColorFont.DROPDOWN_BACKGROUND);
        roomField.setForeground(AppColorFont.DROPDOWN_FOREGROUND);
        roomField.setFont(AppColorFont.FONT_DROPDOWN);
        panel.add(roomField);

        // Confirm button
        confirmButton = new JButton("Confirm your Suspicion");
        confirmButton.setBackground(AppColorFont.BUTTON_BACKGROUND);
        confirmButton.setForeground(AppColorFont.BUTTON_FOREGROUND);
        confirmButton.setFont(AppColorFont.FONT_BUTTON);
        confirmButton.setFocusPainted(false);
        confirmButton.setPreferredSize(new Dimension(0, 80));
        JPanel south = new JPanel(new BorderLayout());
        south.setBackground(AppColorFont.BACKGROUND_DARK);
        south.setBorder(BorderFactory.createEmptyBorder(2, 10, 4, 10));
        south.add(confirmButton, BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);

        setResizable(false);
    }

    public Card getSelectedCharacter() { return (Card) characterBox.getSelectedItem(); }
    public Card getSelectedWeapon()    { return (Card) weaponBox.getSelectedItem(); }
    public JButton getConfirmButton()  { return confirmButton; }
}