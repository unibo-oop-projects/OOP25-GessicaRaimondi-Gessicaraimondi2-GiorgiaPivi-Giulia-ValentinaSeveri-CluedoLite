package it.unibo.cluedolite.model.player.api;

import java.util.List;
import java.util.Optional;

import it.unibo.cluedolite.model.creationcards.impl.Card;

/**
 * Defines the contract for a player in the CluedoLite game.
 * A player has a name, holds a hand of cards,
 * and can match cards during a suspect phase.
 */
public interface Player {

    /**
     * Assigns a character to this player.
     *
     * @param character the character chosen by the player
     */
    void chooseCharacter(CreationCharacter character);

    /**
     * Returns the character chosen by this player.
     *
     * @return the player's character, or {@code null} if not yet chosen
     */
    CreationCharacter getCharacter();

    /**
     * Returns the name of this player.
     *
     * @return the player's name
     */
    String getName();

    /**
     * Adds a card to this player's hand.
     *
     * @param card the card to add
     */
    void addCard(Card card);

    /**
     * Returns the list of cards currently in this player's hand.
     *
     * @return an unmodifiable or live list of the player's cards
     */
    List<Card> getHand();

    /**
     * Searches the player's hand for a card matching any of the three suspect
     * components. The hand is shuffled before searching so that when a player
     * has multiple matching cards they are not always revealed in insertion order.
     *
     * @param character the character card to match
     * @param weapon    the weapon card to match
     * @param room      the room card to match
     * @return an {@link Optional} containing the matching card, or empty if none found
     */
    Optional<Card> findMatchingCard(Card character, Card weapon, Card room);

    /**
     * Marks this player as eliminated after a wrong final accusation.
     * An eliminated player can no longer take actions or move.
     */
    void eliminate();

    /**
     * Returns whether this player has been eliminated from the game.
     *
     * @return {@code true} if the player made a wrong final accusation,
     *         {@code false} otherwise
     */
    boolean isEliminated();

    /**
     * Restores this player to an active state after being eliminated.
     * A restored player can take actions and move again.
     */
    void restore();

    /**
     * Clears all cards from this player's hand.
     */
    void clearHand();
}
