package it.unibo.cluedolite.model.accuseandsuspect.api;

import it.unibo.cluedolite.model.accuseandsuspect.impl.Suspicion;
import it.unibo.cluedolite.model.creationcards.impl.Card;
import it.unibo.cluedolite.model.player.api.Player;

/**
 * Interface for the suspicion manager in the CluedoLite game.
 * It handles the creation of suspicions during the game.
 */
public interface InterfaceSuspicionManager {

    /**
     * Creates a suspicion based on the player's current position and the chosen character and weapon.
     * Returns null if the player is not in a room.
     * @param player the player making the suspicion.
     * @param character the suspected character.
     * @param weapon the suspected weapon.
     * @return a Suspicion object, or null if the player is not in a room.
     */
    Suspicion makeSuspicion(Player player, Card character, Card weapon, Card room);
}