package it.unibo.cluedolite.controller.menucontroller.impl;

import it.unibo.cluedolite.controller.menucontroller.api.StartController;
import it.unibo.cluedolite.view.menuview.LobbyView;
import it.unibo.cluedolite.view.menuview.StartView;

/**
 * Implementation of {@link StartController} that manages the start screen.
 * This class is final because it is not intended to be subclassed.
 */
public final class StartControllerImpl implements StartController {

    @Override
    public void onStartClicked(final StartView view) {
        view.dispose();
        final LobbyControllerImpl lobbyController = new LobbyControllerImpl();
        new LobbyView(lobbyController);
    }
}
