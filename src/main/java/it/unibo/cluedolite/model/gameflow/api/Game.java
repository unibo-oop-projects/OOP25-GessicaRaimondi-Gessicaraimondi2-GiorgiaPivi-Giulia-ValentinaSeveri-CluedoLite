package it.unibo.cluedolite.model.gameflow.api;

import java.util.List;

import it.unibo.cluedolite.model.gameboard.api.GameBoardModel;
import it.unibo.cluedolite.model.player.api.Player;
import it.unibo.cluedolite.model.player.impl.CreationCharacterImpl;
import it.unibo.cluedolite.model.turnmanager.api.TurnManager;

/**
 * Defines the contract for the game model in CluedoLite.
 * Manages the full game lifecycle including player setup,
 * character assignment, state transitions, and access to
 * the board and turn manager.
 */
public interface Game {

    /**
     * Returns the list of players in the game.
     *
     * @return the list of {@link Player} instances
     */
    List<Player> getPlayers();

    /**
     * Returns the list of characters still available to be chosen.
     *
     * @return the list of available {@link CreationCharacterImpl} instances
     */
    List<CreationCharacterImpl> getAvailableCharacters();

    /**
     * Sets a player at the given position in the player list.
     *
     * @param index  the position in the player list
     * @param player the {@link Player} to set
     */
    void setPlayer(int index, Player player);

    /**
     * Assigns a character to the player at the given index.
     *
     * @param index     the index of the player in the player list
     * @param character the {@link CreationCharacterImpl} to assign
     * @throws IllegalStateException    if the player at {@code index} is not initialized
     * @throws IllegalArgumentException if the character is already assigned to another player
     */
    void assignCharacterToPlayer(int index, CreationCharacterImpl character);

    /**
     * Returns the current state of the game.
     *
     * @return the current {@link GameState}
     */
    GameState getState();

    /**
     * Transitions the game from the main menu state to the lobby (waiting) state.
     *
     * @throws IllegalStateException if the game is not in the {@link GameState#MENU} state
     */
    void enterLobby();

    /**
     * Starts the game if all players have a character assigned.
     *
     * @throws IllegalStateException if the game is not in the {@link GameState#WAITING} state
     *                               or if not all players have a character assigned
     */
    void startGame();

    /**
     * Returns whether every player in the game has a character assigned.
     *
     * @return {@code true} if all players have a character, {@code false} otherwise
     */
    boolean allCharactersAssigned();

    /**
     * Resets the game state back to the main menu, clearing all players and board data.
     */
    void quitToMenu();

    /**
     * Restarts the game with the same players, resetting the board and turn manager.
     *
     * @throws IllegalStateException if the game is not in the {@link GameState#IN_PROGRESS} state
     */
    void resetGame();

    /**
     * Returns the game board model.
     *
     * @return the current {@link GameBoardModel}, or {@code null} if the game has not started
     */
    GameBoardModel getGameBoard();

    /**
     * Returns the turn manager responsible for handling player turns.
     *
     * @return the current {@link TurnManager}, or {@code null} if the game has not started
     */
    TurnManager getTurnManager();
}
