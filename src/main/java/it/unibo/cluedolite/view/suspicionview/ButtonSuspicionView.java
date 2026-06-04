package it.unibo.cluedolite.view.suspicionview;

import javax.swing.JButton;
import javax.swing.JPanel;

import it.unibo.cluedolite.controller.accuseandsuspectcontroller.api.InterfaceSuspicionController;
import it.unibo.cluedolite.view.AppColorFont;

/**
 * This class represents the button that triggers the suspicion phase in the game screen.
 * It belongs to the VIEW layer of the MVC pattern.
 *
 * <p>Responsibilities:
 *  - displays a button always visible on the game screen
 *  - when clicked, delegates to the {@link InterfaceSuspicionController} to open the suspicion view
 *
 * <p>This class has no game logic: it only knows the controller and calls
 * {@link InterfaceSuspicionController#openSuspicionView()} when the button is pressed.
 * It does not know anything about the model, the cards, or the suspicion result.
 */
public class ButtonSuspicionView extends JPanel {

    private static final long serialVersionUID = 1L;
    private final JButton suspicionButton;

    /**
     * Constructs the panel containing the suspicion button.
     *
     * @param controller the {@link InterfaceSuspicionController} that handles the suspicion phase.
     */
    @SuppressWarnings("PMD.ConstructorCallsOverridableMethod")
    public ButtonSuspicionView(final InterfaceSuspicionController controller) {

        setBackground(AppColorFont.PANEL_BACKGROUND);

        suspicionButton = new JButton("Make a Suspicion");
        suspicionButton.setBackground(AppColorFont.BUTTON_BACKGROUND);
        suspicionButton.setForeground(AppColorFont.BUTTON_FOREGROUND);
        suspicionButton.setFont(AppColorFont.FONT_BUTTON);
        suspicionButton.setFocusPainted(false);

        suspicionButton.addActionListener(e -> controller.openSuspicionView());

        add(suspicionButton);
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
        suspicionButton.setEnabled(enabled);
    }
}
