package it.unibo.cluedolite.view.endturnbuttonview;

import javax.swing.JButton;

import it.unibo.cluedolite.controller.endturnbuttoncontroller.api.EndTurnController;
import it.unibo.cluedolite.view.AppColorFont;

/**
 * Button that ends the current player's turn.
 */
public final class EndTurnButtonView extends JButton {

    private static final long serialVersionUID = 1L;

    private static final String TEXT = "END TURN";

    /**
     * Creates the END TURN button.
     *
     * @param controller the controller that handles the end turn action
     */
    public EndTurnButtonView(final EndTurnController controller) {
        super(TEXT); // evita ConstructorCallsOverridableMethod su setText()
        setFont(AppColorFont.FONT_BUTTON);
        setBackground(AppColorFont.BUTTON_BACKGROUND);
        setForeground(AppColorFont.BUTTON_FOREGROUND);
        setFocusPainted(false);
        setBorderPainted(false);
        addActionListener(e -> controller.onEndTurnClicked());
    }
}
