package it.unibo.cluedolite.model.suspectnotes.api;

import it.unibo.cluedolite.model.creationcards.impl.Card;

/**
 * Represents a single entry in the suspect notes,
 * storing a card and its current state.
 */
public interface Box {

    /**
     * Marks this card as EXCLUDED.
     */
    void excludeCard();

    /**
     * Returns the current state of this box.
     *
     * @return the {@link State} of this box
     */
    State getState();

    /**
     * Returns the card associated with this box.
     *
     * @return the {@link Card} of this box
     */
    Card getCard();
}