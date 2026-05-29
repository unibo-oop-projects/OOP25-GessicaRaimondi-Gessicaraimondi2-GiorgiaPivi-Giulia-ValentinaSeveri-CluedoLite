package it.unibo.cluedolite.model.gameboard.api;

import java.util.List;

import it.unibo.cluedolite.model.player.api.Player;

/**
 * Model for the game board.
 * Tracks room layout, adjacency, and player positions.
 */
public interface GameBoardModel {

    /**
     * Returns a copy of the list of all rooms in the game board.
     *
     * @return a list containing all the rooms
     */
    List<Room> getRooms();

    /**
     * Returns the room with the given name, or {@code null} if not found.
     *
     * @param name the name of the room to search for
     * @return the matching room, or {@code null} if no room has that name
     */
    Room getRoomByName(String name);

    /**
     * Returns the current room of the given player.
     * Returns {@code null} if the player has no position yet (start of the game).
     *
     * @param p the player whose position is requested
     * @return the room where the player is located, or {@code null} if not yet placed
     */
    Room getPlayerPosition(Player p);

    /**
     * Sets the position of the given player to the specified room.
     *
     * @param p the player whose position is to be set
     * @param r the room where the player will be placed
     */
    void setPlayerPosition(Player p, Room r);

    /**
     * Checks whether two rooms are adjacent to each other.
     *
     * @param r1 the first room
     * @param r2 the second room
     * @return {@code true} if {@code r1} and {@code r2} are adjacent, {@code false} otherwise
     */
    boolean areAdjacent(Room r1, Room r2);

    /**
     * Checks whether the given player can move to the target room.
     * A player with no current position (start of the game) can move to any room.
     * Otherwise, the target room must be adjacent to the player's current position.
     *
     * @param p      the player who wants to move
     * @param target the room the player wants to move to
     * @return {@code true} if the move is allowed, {@code false} otherwise
     */
    boolean canMoveTo(Player p, Room target);
}