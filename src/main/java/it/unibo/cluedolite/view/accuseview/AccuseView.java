package it.unibo.cluedolite.view.accuseview;

import javax.swing.*;

import it.unibo.cluedolite.model.creationcards.impl.Card;
import it.unibo.cluedolite.view.AppColorFont;

import java.awt.*;

public class AccuseView extends JFrame {

    private final JComboBox<Card> characterBox;
    private final JComboBox<Card> weaponBox;
    private final JComboBox<Card> roomBox;
    private JButton confirmButton;

    public AccuseView(Card[] characters, Card[] weapons, Card[] room) {

        setTitle("Make Your Accusation:");
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

        // Row 3: room selection
        JLabel roomLabel = new JLabel("Choose the Room:");
        roomLabel.setForeground(AppColorFont.TEXT_PRIMARY);
        roomLabel.setFont(AppColorFont.FONT_LABEL);
        panel.add(roomLabel);
        roomBox = new JComboBox<>(room);
        roomBox.setBackground(AppColorFont.DROPDOWN_BACKGROUND);
        roomBox.setForeground(AppColorFont.DROPDOWN_FOREGROUND);
        roomBox.setFont(AppColorFont.FONT_DROPDOWN);
        panel.add(roomBox);

        // Confirm button
        confirmButton = new JButton("Confirm your Accusation");
        confirmButton.setBackground(AppColorFont.BUTTON_BACKGROUND);
        confirmButton.setForeground(AppColorFont.BUTTON_FOREGROUND);
        confirmButton.setFont(AppColorFont.FONT_BUTTON);
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
    public Card getSelectedRoom()      { return (Card) roomBox.getSelectedItem(); }
    public JButton getConfirmButton()  { return confirmButton; }
}