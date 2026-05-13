package it.unibo.CluedoLite.model.player.api;

import java.util.List;
import it.unibo.CluedoLite.model.creationcards.impl.Card;
import it.unibo.CluedoLite.model.player.impl.CreationCharacterImpl;

/**
 * Defines the contract for a player in the CluedoLite game
 * A player has a name, holds a hand of cards,
 * and can match cards during a suspect phase
 */
public interface Player {

    /**
     * Assigns a character to this player
     *
     * @param character the character chosen by the player
     */
    void chooseCharacter(CreationCharacterImpl character);

    /**
     * Returns the character chosen by this player
     *
     * @return the player's character, or {@code null} if not yet chosen
     */
    CreationCharacter getCharacter();

    /**
     * Returns the name of this player
     *
     * @return the player's name
     */
    String getName();

    /**
     * Adds a card to this player's hand
     *
     * @param card the card to add
     */
    void addCard(Card card);

    /**
     * Returns the list of cards currently in this player's hand
     *
     * @return an unmodifiable or live list of the player's cards
     */
    List<Card> getHand();

    /**
     * Searches the player's hand for a card matching any of the three suspect
     * components (character, weapon, room) that has not already been excluded
     *
     * @param character the suspect character card
     * @param weapon    the suspect weapon card
     * @param room      the suspect room card
     * @return the first matching non-excluded card, or {@code null} if none found
     */
    Card findMatchingCard(Card character, Card weapon, Card room);

    /**
    * Marks this player as eliminated after a wrong final accusation.
    * An eliminated player can no longer take actions or move.
    */
    void eliminate();

    /**
     * Returns whether this player has been eliminated from the game.
     *
     * @return {@code true} if the player made a wrong final accusation, {@code false} otherwise
     */
    boolean isEliminated();

    /**
    * Marks this player as already moved.
    * this prevent multiple movements in one turn.
    */
    void setMoved(boolean moved);

    /**
     * Returns whether this player has already moved.
     *
     * @return {@code true} if the player has moved, {@code false} otherwise
     */
    boolean hasmoved();

    /**
     * Restores this player to an active state after being eliminated.
     * A restored player can take actions and move again.
     */
    void restore();

}

