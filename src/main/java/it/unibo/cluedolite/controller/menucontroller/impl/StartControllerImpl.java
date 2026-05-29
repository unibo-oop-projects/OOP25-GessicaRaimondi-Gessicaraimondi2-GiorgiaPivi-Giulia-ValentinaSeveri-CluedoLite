package it.unibo.cluedolite.controller.menucontroller.impl;

import it.unibo.cluedolite.controller.menucontroller.api.StartController;
import it.unibo.cluedolite.view.menuview.LobbyView;
import it.unibo.cluedolite.view.menuview.StartView;

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
