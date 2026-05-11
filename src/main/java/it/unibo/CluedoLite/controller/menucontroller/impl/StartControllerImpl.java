package it.unibo.CluedoLite.controller.menucontroller.impl;

import it.unibo.CluedoLite.controller.menucontroller.api.StartController;
import it.unibo.CluedoLite.view.menuview.LobbyView;
import it.unibo.CluedoLite.view.menuview.StartView;

/*
*  Controller that manages the start screen
 */
public class StartControllerImpl implements StartController{
    private StartView view;

    // Creates the controller, saves the view and the model, and connects the buttons
    public StartControllerImpl() {
        
    }

    public void setView(final StartView view) {
        this.view = view;
    }

    // Method executed when Start is clicked
   @Override
    public void onStartClicked() {
        final LobbyControllerImpl lobbyController = new LobbyControllerImpl();
        final LobbyView lobbyView = new LobbyView(lobbyController);
        lobbyView.setVisible(true);
        view.dispose();
    }
}
