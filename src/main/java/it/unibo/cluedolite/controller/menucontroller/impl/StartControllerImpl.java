package it.unibo.cluedolite.controller.menucontroller.impl;

import it.unibo.cluedolite.controller.menucontroller.api.StartController;
import it.unibo.cluedolite.view.menuview.LobbyView;
import it.unibo.cluedolite.view.menuview.StartView;

/**
 * Implementation of {@link StartController} that manages the start screen.
 * Handles the transition from the main menu to the lobby screen.
 */
public class StartControllerImpl implements StartController {

    /**
     * Constructs a new {@code StartControllerImpl}.
     */
    public StartControllerImpl() {
    }

    /**
     * Handles the new game button click event.
     * Closes the start screen and opens the lobby screen.
     *
     * @param view the {@link StartView} representing the start screen to close
     */
    @Override
    public void onStartClicked(final StartView view) {
        view.dispose();
        final LobbyControllerImpl lobbyController = new LobbyControllerImpl();
        new LobbyView(lobbyController);
    }
}