package it.unibo.CluedoLite.controller.menucontroller.impl;

import javax.swing.JOptionPane;

import it.unibo.CluedoLite.controller.GameController;
import it.unibo.CluedoLite.controller.menucontroller.api.LobbyController;
import it.unibo.CluedoLite.model.gameflow.api.Game;
import it.unibo.CluedoLite.model.gameflow.impl.GameImpl;
import it.unibo.CluedoLite.model.player.impl.CreationCharacterImpl;
import it.unibo.CluedoLite.model.player.impl.PlayerImpl;
import it.unibo.CluedoLite.view.menuview.LobbyView;

/**
 * Controller for the LobbyView.
 * Handles player setup and character assignment before the game starts,
 * then transitions to the GameView.
 */
public class LobbyControllerImpl implements LobbyController {

    public LobbyControllerImpl() {}

    @Override
    public void onPlayClicked(final LobbyView view) {
        final int numPlayers = view.getNumPlayers();

        // check se due giocatori hanno lo stesso personaggio
        for (int i = 0; i < numPlayers; i++) {
            for (int j = i + 1; j < numPlayers; j++) {
                if (view.getSelectedCharacter(i).equals(view.getSelectedCharacter(j))) {
                    JOptionPane.showMessageDialog(view, "Two players have the same character");
                    return;
                }
            }
        }

        // crea il Game ora che si conosce il numero di giocatori
        final Game game = new GameImpl(numPlayers);
        game.enterLobby(); // MENU → WAITING

        // assign players and characters
        for (int i = 0; i < numPlayers; i++) {
            final String selectedName = view.getSelectedCharacter(i);
            final CreationCharacterImpl character = game.getAvailableCharacters().stream()
                    .filter(c -> c.getName().equals(selectedName))
                    .findFirst()
                    .get();
            game.setPlayer(i, new PlayerImpl("Player " + (i + 1)));
            game.assignCharacterToPlayer(i, character);
        }

        game.startGame(); // WAITING → IN_PROGRESS
        
        // Delega tutta la sessione di gioco al GameController
        final GameController gameController = new GameController(game);
        gameController.openGameWindow(view);
    }
}
