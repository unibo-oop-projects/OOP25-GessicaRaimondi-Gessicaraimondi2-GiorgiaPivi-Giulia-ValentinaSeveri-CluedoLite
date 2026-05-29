package it.unibo.cluedolite.model.accuseandsuspect.impl;

import it.unibo.cluedolite.model.accuseandsuspect.api.InterfaceSuspicionManager;
import it.unibo.cluedolite.model.creationcards.impl.Card;
import it.unibo.cluedolite.model.player.api.Player;

/**
 * Implementation of the {@link InterfaceSuspicionManager} interface.
 * This class is responsible for creating {@link Suspicion} objects during the suspicion phase.
 *
 * Responsibilities:
 *  - validates that the player is currently in a room before allowing a suspicion
 *  - creates and returns a {@link Suspicion} object with the given character, weapon, and room
 *
 * This class belongs to the MODEL layer and contains no view or controller logic.
 * The room is received directly as a parameter from the controller,
 * since the controller already knows the current room of the player.
 */
public class SuspicionManager implements InterfaceSuspicionManager {

    /**
     * Creates a {@link Suspicion} object based on the player's chosen character and weapon,
     * and the room where the player is currently located.
     *
     * The room is passed directly by the controller rather than retrieved from the board,
     * keeping this class decoupled from the {@link it.unibo.cluedolite.model.gameboard.impl.GameBoardModelImpl}.
     *
     * @param player    the player who is making the suspicion
     * @param character the card representing the suspected character
     * @param weapon    the card representing the suspected weapon
     * @param room      the card representing the room where the player currently is,
     *                  or {@code null} if the player is not in any room
     * @return a new {@link Suspicion} object if the room is valid, {@code null} otherwise
     */
    @Override
    public Suspicion makeSuspicion(Player player, Card character, Card weapon, Card room) {
        // A suspicion can only be made if the player is currently in a room
        if (room == null) {
            System.out.println("The player is not in a room and cannot make a suspicion.");
            return null;
        }
        // Create and return the Suspicion with the chosen character, weapon, and current room
        return new Suspicion(character, weapon, room);
    }
}