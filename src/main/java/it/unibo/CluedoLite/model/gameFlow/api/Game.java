package it.unibo.CluedoLite.model.gameflow.api;

import java.util.List;

import it.unibo.CluedoLite.model.gameboard.api.GameBoardModel;
import it.unibo.CluedoLite.model.player.api.Player;
import it.unibo.CluedoLite.model.player.impl.CreationCharacterImpl;
import it.unibo.CluedoLite.model.turnmanager.api.TurnManager;

/**
 * Defines the contract for the game in CluedoLite
 */
public interface Game {

    /**
     * Returns the list of players in the game
     *
     * @return the list of players
     */
    List<Player> getPlayers();

    /**
     * Returns the list of characters still available to be chosen
     *
     * @return the list of available characters
     */
    List<CreationCharacterImpl> getAvailableCharacters();

    /**
     * Sets a player in the given position
     *
     * @param index  the position
     * @param player the player to set
     */
    void setPlayer(int index, Player player);

    /**
     * Assigns a character to the player at the given index
     *
     * @param index     the player's position
     * @param character the character to assign
     */
    void assignCharacterToPlayer(int index, CreationCharacterImpl character);

    /**
     * Returns the current state of the game
     *
     * @return the game state
     */
    GameState getState();

    /**
     * Moves the game from the main menu to the lobby
     */
    void enterLobby();

    /**
     * Starts the game if all players have a character assigned
     */
    void startGame();

    /**
     * Returns true if every player has a character assigned
     *
     * @return true if all characters are assigned
     */
    boolean allCharactersAssigned();

    /**
     * Returns to the main menu from any state
     */
    void quitToMenu();

    /**
     * Restarts the game with the same players
     */
    void resetGame();

    GameBoardModel getGameBoard();

    TurnManager getTurnManager();
}
