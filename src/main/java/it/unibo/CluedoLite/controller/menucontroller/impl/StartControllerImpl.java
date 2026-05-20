package it.unibo.CluedoLite.controller.menucontroller.impl;

import it.unibo.CluedoLite.controller.menucontroller.api.StartController;
import it.unibo.CluedoLite.view.menuview.LobbyView;
import it.unibo.CluedoLite.view.menuview.StartView;

/*
*  Controller that manages the start screen
 */
public class StartControllerImpl implements StartController{

    public StartControllerImpl() {
        
    }

    /**
    * Called when the user clicks NEW GAME.
    * Closes the start screen and opens the lobby.
    *
    * @param view the start screen to close
    */
   @Override
    public void onStartClicked(StartView view) {
        view.dispose();  
        final LobbyControllerImpl lobbyController = new LobbyControllerImpl();
        new LobbyView(lobbyController);      
    }
}
