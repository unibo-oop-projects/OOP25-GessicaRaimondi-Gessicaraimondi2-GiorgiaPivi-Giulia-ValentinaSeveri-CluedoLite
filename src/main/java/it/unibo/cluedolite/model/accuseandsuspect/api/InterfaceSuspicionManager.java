package it.unibo.cluedolite.model.accuseandsuspect.api;

import it.unibo.cluedolite.model.creationcards.impl.AbstractCard;
import it.unibo.cluedolite.model.player.api.Player;

/**
 * Interface for the suspicion manager in the CluedoLite game.
 * It handles the creation of suspicions during the game.
 */
@FunctionalInterface
public interface InterfaceSuspicionManager {

    /**
     * Creates a suspicion based on the player's current position and the chosen character and weapon.
     * Returns null if the player is not in a room.
     * 
     * @param player the player making the suspicion.
     * @param character the suspected character.
     * @param weapon the suspected weapon.
     * @param room where the player is currently located.
     * @return a {@link InterfaceSuspicion} object representing the suspicion
     */
    InterfaceSuspicion makeSuspicion(Player player, AbstractCard character, AbstractCard weapon, AbstractCard room);
}
