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
 * Implementation of {@link LobbyController} that manages the lobby screen.
 * Handles player setup and character assignment before the game starts,
 * then transitions to the game view.
 */
public class LobbyControllerImpl implements LobbyController {

    /**
     * Constructs a new {@code LobbyControllerImpl}.
     */
    public LobbyControllerImpl() {
    }

    /**
     * Handles the play button click event.
     * Validates that no two players have selected the same character,
     * builds the game model, assigns players and characters,
     * starts the game, and opens the game window.
     *
     * @param view the {@link LobbyView} from which player and character data are read
     */
    @Override
    public void onPlayClicked(final LobbyView view) {
        final int numPlayers = view.getNumPlayers();

        for (int i = 0; i < numPlayers; i++) {
            for (int j = i + 1; j < numPlayers; j++) {
                if (view.getSelectedCharacterName(i).equals(view.getSelectedCharacterName(j))) {
                    JOptionPane.showMessageDialog(view, "Two players have the same character.");
                    return;
                }
            }
        }

        final Game game = new GameImpl(numPlayers);
        game.enterLobby();

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