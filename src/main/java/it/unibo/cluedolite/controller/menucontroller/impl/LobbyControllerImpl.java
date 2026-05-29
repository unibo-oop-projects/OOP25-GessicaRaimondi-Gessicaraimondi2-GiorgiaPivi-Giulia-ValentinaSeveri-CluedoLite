package it.unibo.cluedolite.controller.menucontroller.impl;

import javax.swing.JOptionPane;

import it.unibo.cluedolite.controller.GameController;
import it.unibo.cluedolite.controller.menucontroller.api.LobbyController;
import it.unibo.cluedolite.model.gameflow.api.Game;
import it.unibo.cluedolite.model.gameflow.impl.GameImpl;
import it.unibo.cluedolite.model.player.impl.CreationCharacterImpl;
import it.unibo.cluedolite.model.player.impl.PlayerImpl;
import it.unibo.cluedolite.view.menuview.LobbyView;

/**
 * Controller for the LobbyView.
 * Handles player setup and character assignment before the game starts,
 * then transitions to the GameView.
 */
public class LobbyControllerImpl implements LobbyController {

    public LobbyControllerImpl() {}
     /**
     * Called when the user clicks START PLAY.
     * Validates character uniqueness, builds the game model,
     * assigns players and characters, starts the game,
     * then opens the game window.
     *
     * @param view the lobby view from which player and character data are read
     */
    @Override
    public void onPlayClicked(final LobbyView view) {
        final int numPlayers = view.getNumPlayers();

        // check se due giocatori hanno lo stesso personaggio
        for (int i = 0; i < numPlayers; i++) {
            for (int j = i + 1; j < numPlayers; j++) {
                if (view.getSelectedCharacterName(i).equals(view.getSelectedCharacterName(j))) {
                    JOptionPane.showMessageDialog(view, "Two players have the same character");
                    return;
                }
            }
        }

        final Game game = new GameImpl(numPlayers);
        game.enterLobby(); 

        // assign players and characters
        for (int i = 0; i < numPlayers; i++) {
            final String selectedName = view.getSelectedCharacterName(i);
            final CreationCharacterImpl character = game.getAvailableCharacters().stream()
                    .filter(c -> c.getName().equals(selectedName))
                    .findFirst()
                    .get();
            game.setPlayer(i, new PlayerImpl("Player " + (i + 1)));
            game.assignCharacterToPlayer(i, character);
        }

        game.startGame(); 
        
        final GameController gameController = new GameController(game);
        gameController.openGameWindow(view);
    }
}
