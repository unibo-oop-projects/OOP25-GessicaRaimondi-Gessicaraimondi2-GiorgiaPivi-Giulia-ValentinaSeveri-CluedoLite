package it.unibo.cluedolite.view.buttonflowview;

import javax.swing.JButton;

import it.unibo.cluedolite.controller.buttonflowcontroller.api.QuitButtonController;
import it.unibo.cluedolite.view.AppColorFont;

/**
 * A styled button that quits the current game and returns to the main menu.
 * Delegates the quit action to the provided {@link QuitButtonController}.
 */
public final class QuitButtonView extends JButton {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs the QUIT button and registers the quit action listener.
     *
     * @param controller the {@link QuitButtonController} that handles the quit action
     */
    public QuitButtonView(final QuitButtonController controller) {
        setText("QUIT");
        setFont(AppColorFont.FONT_BUTTON);
        setBackground(AppColorFont.BUTTON_BACKGROUND);
        setForeground(AppColorFont.BUTTON_FOREGROUND);
        setFocusPainted(false);
        setBorderPainted(false);
        addActionListener(e -> controller.onQuitClicked());
    }
}
