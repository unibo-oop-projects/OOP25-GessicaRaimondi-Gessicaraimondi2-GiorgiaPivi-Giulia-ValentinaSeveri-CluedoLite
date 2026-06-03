package it.unibo.cluedolite.controller.gameboardcontroller.impl;

import java.util.Objects;

import it.unibo.cluedolite.controller.gameboardcontroller.api.GameBoardController;
import it.unibo.cluedolite.model.gameboard.api.GameBoardModel;
import it.unibo.cluedolite.model.gameboard.api.Room;
import it.unibo.cluedolite.model.player.api.Player;
import it.unibo.cluedolite.model.turnmanager.api.TurnManager;
import it.unibo.cluedolite.view.gameboardview.api.BoardView;

/**
 * Implementation of {@link GameBoardController}.
 * Manages player movement on the board and delegates turn advancement
 * to the provided {@link TurnManager}.
 */
@SuppressWarnings("PMD.ExposeInternalRepresentation")
public final class GameBoardControllerImpl implements GameBoardController {

    private final GameBoardModel gb;
    private final TurnManager tm;
    private BoardView view;
    private Room turnStartRoom;
    private boolean movementLocked;

    /**
     * Creates a new {@code GameBoardControllerImpl}.
     *
     * @param gb the game board model
     * @param tm the turn manager
     */
    public GameBoardControllerImpl(final GameBoardModel gb, final TurnManager tm) {
        this.gb = Objects.requireNonNull(gb, "gb must not be null");
        this.tm = Objects.requireNonNull(tm, "tm must not be null");
        this.turnStartRoom = null;
        this.movementLocked = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setView(final BoardView v) {
        this.view = Objects.requireNonNull(v, "view must not be null");
    }

    /**
     * Moves the current player to room {@code r}, following these rules:
     *   If movement is locked (suggestion/accusation already made) does nothing.
     *   On the very first turn (no position yet) any room is valid
     *       and becomes the {@code turnStartRoom}.
     *   On subsequent turns the player may only move to the turn-start room
     *       or one of its two adjacent rooms.
     */
    @Override
    public void move(final Room r) {
        if (r == null) {
            return;
        }

        if (movementLocked) {
            return;
        }

        final Player currentPlayer = tm.getCurrentPlayer();
        final Room currentPos = gb.getPlayerPosition(currentPlayer);

        if (currentPos == null) {
            gb.setPlayerPosition(currentPlayer, r);
            turnStartRoom = r;
            view.repaint();
            return;
        }

        if (turnStartRoom == null) {
            turnStartRoom = currentPos;
        }

        if (r.equals(turnStartRoom) || turnStartRoom.getAdjacent().contains(r)) {
            gb.setPlayerPosition(currentPlayer, r);
            view.repaint();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void lockMovement() {
        movementLocked = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player currentPlayer() {
        return tm.getCurrentPlayer();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Room getRoomByName(final String name) {
        return gb.getRoomByName(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Room getCurrentRoomOf(final Player p) {
        return gb.getPlayerPosition(p);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void endTurn() {
        turnStartRoom = null;
        movementLocked = false;
        tm.nextTurn();
        view.repaint();
    }
}
