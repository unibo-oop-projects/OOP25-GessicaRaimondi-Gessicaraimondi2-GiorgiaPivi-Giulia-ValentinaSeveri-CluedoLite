package it.unibo.cluedolite.model.gamesetup.impl;

import java.util.List;

import it.unibo.cluedolite.model.creationcards.impl.Card;
import it.unibo.cluedolite.model.player.api.Player;

/**
 * Responsible for distributing cards to players at the beginning of the game.
 * Cards are dealt one at a time in a round-robin fashion until all cards are distributed.
 */
public class CardDistribution {

    /**
     * Constructs a {@link CardDistribution} and immediately distributes the given cards
     * among the players in a round-robin fashion.
     *
     * @param cards   the list of {@link Card} objects to be distributed
     * @param players the list of {@link Player} objects that will receive the cards
     */
    public CardDistribution(List<Card> cards, List<Player> players) {
        int numCards = cards.size();
        int cardIndex = 0;

        while (cardIndex < numCards) {
            for (Player player : players) {
                if (cardIndex < numCards) {
                    player.addCard(cards.get(cardIndex));
                    cardIndex++;
                }
            }
        }
    }
}
