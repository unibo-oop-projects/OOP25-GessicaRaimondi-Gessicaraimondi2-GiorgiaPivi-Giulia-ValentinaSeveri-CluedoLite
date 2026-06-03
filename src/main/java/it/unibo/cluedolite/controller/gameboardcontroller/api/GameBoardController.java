package it.unibo.cluedolite.controller.gameboardcontroller.api;

import it.unibo.cluedolite.model.gameboard.api.Room;
import it.unibo.cluedolite.model.player.api.Player;
import it.unibo.cluedolite.view.gameboardview.api.BoardView;

/**
 * Controller for the game board.
 * Manages player movement and turn progression.
 */
public interface GameBoardController {

    /**
     * Sets the view for this controller.
     * Must be called after construction to complete the MVC wiring.
     *
     * @param v the board view
     */
    void setView(BoardView v);

    /**
     * Moves the current player to the specified room if the move is valid.
     * If the move is not valid, notifies the view.
     *
     * @param r the target room
     */
    void move(Room r);

    /**
     * Locks the current player's movement for the rest of the turn.
     * Called by {@code GameController} after a suggestion or accusation.
     */
    void lockMovement();

    /**
     * Returns the player whose turn it currently is.
     *
     * @return the current player
     */
    Player currentPlayer();

    /**
     * Returns the room with the given name.
     *
     * @param name the name of the room
     * @return the corresponding room, or null if not found
     */
    Room getRoomByName(String name);

    /**
     * Returns the current room of the given player.
     *
     * @param p the player
     * @return the room the player is currently in, or null if not placed yet
     */
    Room getCurrentRoomOf(Player p);

    /**
     * Ends the current player's turn and advances to the next player.
     */
    void endTurn();
}
