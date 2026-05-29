package it.unibo.cluedolite.model.accuseandsuspect.api;

import it.unibo.cluedolite.model.creationcards.impl.Card;

/**
 * Interface for a suspicion made by a player in the CluedoLite game.
 * A suspicion is composed of a character, a weapon and a room.
 */
public interface InterfaceSuspicion {

    /**
     * Returns the suspected character.
     * @return the character card suspected by the player.
     */
    Card getCharacters();

    /**
     * Returns the suspected weapon.
     * @return the weapon card suspected by the player.
     */
    Card getWeapon();

    /**
     * Returns the room where the suspicion is made.
     * @return the room where the player is located.
     */
    Card getRoom();
}