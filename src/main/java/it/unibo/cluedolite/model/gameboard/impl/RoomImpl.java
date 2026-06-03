package it.unibo.cluedolite.model.gameboard.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import it.unibo.cluedolite.model.gameboard.api.Room;

/**
 * Implementation of {@link Room}.
 * Represents a named room on the game board with a list of adjacent rooms.
 */
public final class RoomImpl implements Room {

    private final String name;
    private final List<Room> adjacent = new ArrayList<>();

    /**
     * Creates a new {@code RoomImpl} with the given name.
     *
     * @param name the name of the room
     */
    public RoomImpl(final String name) {
        this.name = Objects.requireNonNull(name, "name must not be null");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAdjacent(final Room r) {
        adjacent.add(r);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Room> getAdjacent() {
        return List.copyOf(adjacent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }
}
