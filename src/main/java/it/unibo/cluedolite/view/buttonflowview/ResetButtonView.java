package it.unibo.cluedolite.view.buttonflowview;

import javax.swing.JButton;

import it.unibo.cluedolite.controller.buttonflowcontroller.api.ResetButtonController;
import it.unibo.cluedolite.view.AppColorFont;

/**
 * A styled button that resets the current game while keeping the same players.
 * Delegates the reset action to the provided {@link ResetButtonController}.
 */
public final class ResetButtonView extends JButton {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs the RESET button and registers the reset action listener.
     *
     * @param controller the {@link ResetButtonController} that handles the reset action
     */
    public ResetButtonView(final ResetButtonController controller) {
        setText("RESET");
        setFont(AppColorFont.FONT_BUTTON);
        setBackground(AppColorFont.BUTTON_BACKGROUND);
        setForeground(AppColorFont.BUTTON_FOREGROUND);
        setFocusPainted(false);
        setBorderPainted(false);
        addActionListener(e -> controller.onResetClicked());
    }
}
