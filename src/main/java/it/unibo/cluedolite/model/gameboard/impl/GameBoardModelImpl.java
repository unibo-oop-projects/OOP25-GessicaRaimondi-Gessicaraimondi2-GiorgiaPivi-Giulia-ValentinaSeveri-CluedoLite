package it.unibo.cluedolite.model.gameboard.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unibo.cluedolite.model.gameboard.api.GameBoardModel;
import it.unibo.cluedolite.model.gameboard.api.Room;
import it.unibo.cluedolite.model.player.api.Player;

/**
 * Implementation of {@link GameBoardModel}.
 * Initialises the nine classic Cluedo rooms and wires their adjacencies
 * in a circular order.
 */
public final class GameBoardModelImpl implements GameBoardModel {

    private final Map<Player, Room> playerPositions = new HashMap<>();
    private final List<Room> rooms = new ArrayList<>();

    /**
     * Creates a new {@code GameBoardModelImpl}.
     * Populates the board with the nine standard Cluedo rooms and
     * automatically generates circular adjacencies between them.
     */
    public GameBoardModelImpl() {
        rooms.add(new RoomImpl("Kitchen"));
        rooms.add(new RoomImpl("Ballroom"));
        rooms.add(new RoomImpl("Conservatory"));
        rooms.add(new RoomImpl("Billiard Room"));
        rooms.add(new RoomImpl("Library"));
        rooms.add(new RoomImpl("Study"));
        rooms.add(new RoomImpl("Hall"));
        rooms.add(new RoomImpl("Lounge"));
        rooms.add(new RoomImpl("Dining Room"));

        // Automatically generates adjacencies in a circular order
        for (int i = 0; i < rooms.size(); i++) {
            rooms.get(i).addAdjacent(rooms.get((i + 1) % rooms.size()));
            rooms.get(i).addAdjacent(rooms.get((i - 1 + rooms.size()) % rooms.size()));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Room> getRooms() {
        return List.copyOf(rooms);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Room getRoomByName(final String name) {
        for (final Room r : rooms) {
            if (r.getName().equalsIgnoreCase(name)) {
                return r;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Room getPlayerPosition(final Player p) {
        return playerPositions.get(p);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPlayerPosition(final Player p, final Room r) {
        playerPositions.put(p, r);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean areAdjacent(final Room r1, final Room r2) {
        return r1.getAdjacent().contains(r2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canMoveTo(final Player p, final Room target) {
        final Room current = playerPositions.get(p);
        if (current == null) {
            // At game start the player may choose any room
            return true;
        }
        return current.getAdjacent().contains(target);
    }
}