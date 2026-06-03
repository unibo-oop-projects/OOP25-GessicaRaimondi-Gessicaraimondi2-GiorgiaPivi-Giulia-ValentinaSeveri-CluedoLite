package it.unibo.cluedolite.model.turnmanager.api;

import java.util.Optional;

import it.unibo.cluedolite.model.accuseandsuspect.api.InterfaceSuspicion;
import it.unibo.cluedolite.model.creationcards.impl.AbstractCard;
import it.unibo.cluedolite.model.player.api.Player;

/**
 * Interface rapresenting the logic of the class that manages 
 * the turn order of players in the game.
 */
public interface TurnManager {

    /**
     * Returns the player whose turn it currently is.
     *
     * @return the current player
     */
    Player getCurrentPlayer();

    /**
     * Marks the game as over, preventing any further turn progression.
     */
    void endGame();

    /**
     * Checks whether the game has ended.
     *
     * @return true if the game is over, false otherwise
     */
    boolean isGameOver();

    /**
     * Advances the turn to the next player in circular order.
     * Throws an exception if the game is already over.
     *
     * @return the next player
     * @throws IllegalStateException if the game is already over
     */
    Player nextTurn();

    /**
     * Handles the response to the current player's suggestion.
     * The other players, in circular order, show a randomly selected card
     * from those matching the suggestion.
     *
     * @param suspicion the suspected cards
     * @return an Optional containing the card shown by the first player who
     *         can respond, or empty if no one can refute the suspicion
     */
    Optional<AbstractCard> checkSuspicion(InterfaceSuspicion suspicion);

    /**
     * Returns the number of the player who has shown a card in response to the suspicion.
     *
     * @return the number of the player who showed the card
     */
    int getShownBy();
}
