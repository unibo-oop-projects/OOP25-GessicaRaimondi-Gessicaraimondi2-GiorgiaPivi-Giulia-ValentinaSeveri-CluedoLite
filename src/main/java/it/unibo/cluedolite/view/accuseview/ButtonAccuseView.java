package it.unibo.cluedolite.view.accuseview;

import javax.swing.JButton;
import javax.swing.JPanel;

import it.unibo.cluedolite.controller.accuseandsuspectcontroller.api.InterfaceAccusation;
import it.unibo.cluedolite.view.AppColorFont;

/**
 * This class represents the button that triggers the accusation phase in the game screen.
 * It belongs to the VIEW layer of the MVC pattern.
 *
 * <p>Responsibilities:
 *  - displays a button always visible on the game screen
 *  - when clicked, delegates to the {@link InterfaceAccusationController} to open the accusation view
 *
 * <p>This class has no game logic: it only knows the controller and calls
 * {@link InterfaceAccusationController#openAccusationView()} when the button is pressed.
 * It does not know anything about the model, the cards, or the accusation result.
 */
@SuppressWarnings("PMD.ConstructorCallsOverridableMethod")
public class ButtonAccuseView extends JPanel {

    private static final long serialVersionUID = 1L;
    private final JButton accusationButton;

    /**
     * Constructs the panel containing the accusation button.
     *
     * @param controller the {@link InterfaceAccusation} controller that handles the accusation phase.
     */
    public ButtonAccuseView(final InterfaceAccusation controller) {

        setBackground(AppColorFont.PANEL_BACKGROUND);

        accusationButton = new JButton("Make an Accusation");
        accusationButton.setBackground(AppColorFont.BUTTON_BACKGROUND);
        accusationButton.setForeground(AppColorFont.BUTTON_FOREGROUND);
        accusationButton.setFont(AppColorFont.FONT_BUTTON);
        accusationButton.setFocusPainted(false);

        accusationButton.addActionListener(e -> controller.openAccusationView());

        add(accusationButton);
    }

    /**
     * Enables or disables both the panel and the inner button.
     * Overridden because setEnabled on a JPanel does not propagate to children.
     * 
     * @param enabled {@code true} to enable the button, {@code false} to disable it
     */
    @Override
    public void setEnabled(final boolean enabled) {
        super.setEnabled(enabled);
        accusationButton.setEnabled(enabled);
    }
}
