package it.unibo.cluedolite.model.creationcards.api;

import it.unibo.cluedolite.model.creationcards.impl.CardType;
/**
 * Represents the contract for a generic card in the game.
 * Any object that can be used as a card must implement this interface.
 */
public interface InterfaceCard {

    /**
     * Returns the name of the card.
     * @return the card name
     */
    String getName();

    /**
     * Returns the type of the card (CHARACTER, WEAPON, ROOM).
     * @return the CardType enum value
     */
    CardType getType();

    /**
     * Returns a string representation of the card.
     * @return the card name as string
     */
    String toString();
}