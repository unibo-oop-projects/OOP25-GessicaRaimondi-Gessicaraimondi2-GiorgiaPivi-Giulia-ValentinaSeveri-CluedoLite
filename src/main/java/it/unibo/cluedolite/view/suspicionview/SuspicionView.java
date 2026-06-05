package it.unibo.cluedolite.view.suspicionview;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import it.unibo.cluedolite.model.creationcards.impl.AbstractCard;
import it.unibo.cluedolite.view.AppColorFont;

/**
 * This class represents the Swing VIEW for the suspicion phase of the CluedoLite game.
 * Responsibilities:
 * - displays to the player:
 *      - the room they are currently in (not editable, determined by the game)
 *      - a dropdown list of selectable characters
 *      - a dropdown list of selectable weapons
 *  - exposes getter methods so the controller can read the player's choices
 *  - exposes the confirm button so the controller can attach the confirmation logic
 * This class contains NO game logic: it only handles presentation and input collection.
 * It does not know what happens after the player confirms — that is the controller's responsibility.
 */
public final class SuspicionView extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final int WINDOW_WIDTH = 700;
    private static final int WINDOW_HEIGHT = 550;
    private static final int GRID_ROWS = 3;
    private static final int GRID_COLS = 2;
    private static final int GRID_GAP = 10;
    private static final int CONFIRM_BUTTON_HEIGHT = 80;
    private static final int SOUTH_BORDER_TOP = 2;
    private static final int SOUTH_BORDER_SIDE = 10;
    private static final int SOUTH_BORDER_BOTTOM = 4;

    private final JComboBox<AbstractCard> characterBox;
    private final JComboBox<AbstractCard> weaponBox;
    private final JTextField roomField;
    private final JButton confirmButton;

    /**
     * Constructs the suspicion view and initializes all its components.
     *
     * @param characters array of {@link AbstractCard} objects representing the available characters
     * @param weapons    array of {@link AbstractCard} objects representing the available weapons
     * @param room       {@link AbstractCard} representing the room where the player is currently located
     */
    public SuspicionView(final AbstractCard[] characters, final AbstractCard[] weapons, final AbstractCard room) {
        setTitle("Make Your Suspicion:");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
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

        final JLabel roomLabel = new JLabel("The Room is:");
        roomLabel.setForeground(AppColorFont.TEXT_PRIMARY);
        roomLabel.setFont(AppColorFont.FONT_LABEL);
        panel.add(roomLabel);
        roomField = new JTextField(room.getName());
        roomField.setEditable(false);
        roomField.setBackground(AppColorFont.DROPDOWN_BACKGROUND);
        roomField.setForeground(AppColorFont.DROPDOWN_FOREGROUND);
        roomField.setFont(AppColorFont.FONT_DROPDOWN);
        panel.add(roomField);

        confirmButton = new JButton("Confirm your Suspicion");
        confirmButton.setBackground(AppColorFont.BUTTON_BACKGROUND);
        confirmButton.setForeground(AppColorFont.BUTTON_FOREGROUND);
        confirmButton.setFont(AppColorFont.FONT_BUTTON);
        confirmButton.setFocusPainted(false);
        confirmButton.setPreferredSize(new Dimension(0, CONFIRM_BUTTON_HEIGHT));

        final JPanel south = new JPanel(new BorderLayout());
        south.setBackground(AppColorFont.BACKGROUND_DARK);
        south.setBorder(BorderFactory.createEmptyBorder(
            SOUTH_BORDER_TOP, SOUTH_BORDER_SIDE, SOUTH_BORDER_BOTTOM, SOUTH_BORDER_SIDE));
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
