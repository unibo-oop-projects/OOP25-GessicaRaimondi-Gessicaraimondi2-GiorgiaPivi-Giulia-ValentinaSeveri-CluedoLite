package it.unibo.cluedolite.view.accuseview;

import it.unibo.cluedolite.model.creationcards.impl.AbstractCard;
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
public final class AccuseView extends JFrame {

    private static final int FRAME_WIDTH = 700;
    private static final int FRAME_HEIGHT = 550;
    private static final int GRID_ROWS = 3;
    private static final int GRID_COLS = 2;
    private static final int GRID_GAP = 10;
    private static final int BUTTON_HEIGHT = 80;
    private static final int BORDER_TOP = 2;
    private static final int BORDER_BOTTOM = 4;
    private static final long serialVersionUID = 1L;

    private final JComboBox<AbstractCard> characterBox;
    private final JComboBox<AbstractCard> weaponBox;
    private final JComboBox<AbstractCard> roomBox;
    private final JButton confirmButton;

    /**
     * Constructs the AccuseView with the given arrays of cards.
     * 
     * @param characters array of character cards to display
     * @param weapons    array of weapon cards to display
     * @param room       array of room cards to display
     */
    public AccuseView(final AbstractCard[] characters, final AbstractCard[] weapons, final AbstractCard[] room) {

        setTitle("Make Your Accusation:");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLayout(new BorderLayout());

        final JPanel panel = new JPanel(new GridLayout(GRID_ROWS, GRID_COLS, GRID_GAP, GRID_GAP));
        panel.setBackground(AppColorFont.BACKGROUND_DARK);
        add(panel, BorderLayout.CENTER);

        final JLabel charLabel = new JLabel("Choose the Character:");
        charLabel.setForeground(AppColorFont.TEXT_PRIMARY);
        charLabel.setFont(AppColorFont.FONT_LABEL);
        panel.add(charLabel);
        characterBox = new JComboBox<>(characters);
        characterBox.setBackground(AppColorFont.DROPDOWN_BACKGROUND);
        characterBox.setForeground(AppColorFont.DROPDOWN_FOREGROUND);
        characterBox.setFont(AppColorFont.FONT_DROPDOWN);
        panel.add(characterBox);

        final JLabel weapLabel = new JLabel("Choose the Weapon:");
        weapLabel.setForeground(AppColorFont.TEXT_PRIMARY);
        weapLabel.setFont(AppColorFont.FONT_LABEL);
        panel.add(weapLabel);
        weaponBox = new JComboBox<>(weapons);
        weaponBox.setBackground(AppColorFont.DROPDOWN_BACKGROUND);
        weaponBox.setForeground(AppColorFont.DROPDOWN_FOREGROUND);
        weaponBox.setFont(AppColorFont.FONT_DROPDOWN);
        panel.add(weaponBox);

        final JLabel roomLabel = new JLabel("Choose the Room:");
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
        confirmButton.setPreferredSize(new Dimension(0, BUTTON_HEIGHT));
        final JPanel south = new JPanel(new BorderLayout());
        south.setBackground(AppColorFont.BACKGROUND_DARK);
        south.setBorder(BorderFactory.createEmptyBorder(BORDER_TOP, GRID_GAP, BORDER_BOTTOM, GRID_GAP));
        south.add(confirmButton, BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);

        setResizable(false);
    }

    /**
     * Returns the character card selected by the player.
     * 
     * @return the selected character {@link AbstractCard}
     */
    public AbstractCard getSelectedCharacter() {
        return (AbstractCard) characterBox.getSelectedItem();
    }

    /**
     * Returns the weapon card selected by the player.
     * 
     * @return the selected weapon {@link AbstractCard}
     */
    public AbstractCard getSelectedWeapon() {
        return (AbstractCard) weaponBox.getSelectedItem();
    }

    /**
     * Returns the room card selected by the player.
     * 
     * @return the selected room {@link AbstractCard}
     */
    public AbstractCard getSelectedRoom() {
        return (AbstractCard) roomBox.getSelectedItem();
    }

    /**
     * Attaches an action listener to the confirm button.
     *
     * @param listener the {@link java.awt.event.ActionListener} to attach
     */
    public void addConfirmListener(final java.awt.event.ActionListener listener) {
        confirmButton.addActionListener(listener);
    }

    /**
     * Enables or disables the confirm button.
     *
     * @param enabled {@code true} to enable the button, {@code false} to disable it
     */
    public void setConfirmEnabled(final boolean enabled) {
        confirmButton.setEnabled(enabled);
    }
}
