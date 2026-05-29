package it.unibo.cluedolite.view.accuseview;

import it.unibo.cluedolite.model.creationcards.impl.Card;
import it.unibo.cluedolite.view.AppColorFont;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

/**
 * View for the accusation phase of the CluedoLite game.
 * Displays a dialog allowing the player to select a character, a weapon, and a room
 * to make a formal accusation. The confirm button triggers the accusation logic.
 */
public class AccuseView extends JFrame {

    private final JComboBox<Card> characterBox;
    private final JComboBox<Card> weaponBox;
    private final JComboBox<Card> roomBox;
    private final JButton confirmButton;

    /**
    * Constructs the AccuseView with the given arrays of cards.
    * @param characters array of character cards to display
    * @param weapons    array of weapon cards to display
    * @param room       array of room cards to display
    */
    public AccuseView(Card[] characters, Card[] weapons, Card[] room) {

        setTitle("Make Your Accusation:");
        setSize(700, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBackground(AppColorFont.BACKGROUND_DARK);
        add(panel, BorderLayout.CENTER);

        JLabel charLabel = new JLabel("Choose the Character:");
        charLabel.setForeground(AppColorFont.TEXT_PRIMARY);
        charLabel.setFont(AppColorFont.FONT_LABEL);
        panel.add(charLabel);
        characterBox = new JComboBox<>(characters);
        characterBox.setBackground(AppColorFont.DROPDOWN_BACKGROUND);
        characterBox.setForeground(AppColorFont.DROPDOWN_FOREGROUND);
        characterBox.setFont(AppColorFont.FONT_DROPDOWN);
        panel.add(characterBox);

        JLabel weapLabel = new JLabel("Choose the Weapon:");
        weapLabel.setForeground(AppColorFont.TEXT_PRIMARY);
        weapLabel.setFont(AppColorFont.FONT_LABEL);
        panel.add(weapLabel);
        weaponBox = new JComboBox<>(weapons);
        weaponBox.setBackground(AppColorFont.DROPDOWN_BACKGROUND);
        weaponBox.setForeground(AppColorFont.DROPDOWN_FOREGROUND);
        weaponBox.setFont(AppColorFont.FONT_DROPDOWN);
        panel.add(weaponBox);

        JLabel roomLabel = new JLabel("Choose the Room:");
        roomLabel.setForeground(AppColorFont.TEXT_PRIMARY);
        roomLabel.setFont(AppColorFont.FONT_LABEL);
        panel.add(roomLabel);
        roomBox = new JComboBox<>(room);
        roomBox.setBackground(AppColorFont.DROPDOWN_BACKGROUND);
        roomBox.setForeground(AppColorFont.DROPDOWN_FOREGROUND);
        roomBox.setFont(AppColorFont.FONT_DROPDOWN);
        panel.add(roomBox);

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

    /**
    * Returns the character card selected by the player.
    * @return the selected character {@link Card}
    */
    public Card getSelectedCharacter() { 
        return (Card) characterBox.getSelectedItem(); 
    }

    /**
    * Returns the weapon card selected by the player.
    * @return the selected weapon {@link Card}
    */
    public Card getSelectedWeapon() { 
        return (Card) weaponBox.getSelectedItem(); 
    }
     
    /**
    * Returns the room card selected by the player.
    * @return the selected room {@link Card}
    */
    public Card getSelectedRoom() { 
        return (Card) roomBox.getSelectedItem(); 
    }

    /**
    * Returns the confirm button so the controller can attach the action listener.
    * @return the confirm {@link JButton}
    */
    public JButton getConfirmButton() { 
        return confirmButton; 
    }
}