package it.unibo.cluedolite.model.suspectnotes.api;

import java.util.List;

import it.unibo.cluedolite.model.creationcards.impl.Card;
import it.unibo.cluedolite.model.suspectnotes.impl.*;

public interface Table {
    
    /**
     * Updates the table based on the player's hand, marking the corresponding cards as EXCLUDED.
     *
     * @param hand the list of cards in the player's hand
     */
    void initializeTable(List<Card> hand);
    
    /**
     * Returns the list of Boxes corresponding to the given card's type.
     *
     * @param name the card whose type determines the list to return
     * @return a {@link List} of {@link Box} of the same type as the given card
     */
    List<BoxImpl> searchType(Card name);
    
    /**
     * Checks whether the given card is already marked as EXCLUDED in the table.
     *
     * @param name the card to check
     * @return {@code true} if the card is EXCLUDED, {@code false} otherwise
     */
    boolean alreadyExcluded(Card name);
    
    /**
     * Marks the box corresponding to the given card as EXCLUDED.
     *
     * @param name the card to exclude
     */
    void updateTable(Card name);
}