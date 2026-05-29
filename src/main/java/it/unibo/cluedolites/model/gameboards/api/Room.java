package it.unibo.cluedolite.model.gameboard.api;

import java.util.List;

/**
 * Represents a room on the game board.
 * Each room has a name and a set of adjacent rooms reachable from it.
 */
public interface Room {

    /**
     * Adds a room to the adjacency list of this room.
     *
     * @param r the room to mark as adjacent
     */
    void addAdjacent(Room r);

    /**
     * Returns the list of rooms directly adjacent to this room.
     *
     * @return a list of adjacent rooms
     */
    List<Room> getAdjacent();

    /**
     * Returns the name of this room.
     *
     * @return the room name
     */
    String getName();
}