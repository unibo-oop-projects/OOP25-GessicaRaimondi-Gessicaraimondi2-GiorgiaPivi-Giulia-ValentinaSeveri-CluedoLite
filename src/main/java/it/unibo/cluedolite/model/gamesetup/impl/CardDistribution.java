package it.unibo.cluedolite.model.gamesetup.impl;

import java.util.List;
import java.util.stream.IntStream;

import it.unibo.cluedolite.model.creationcards.impl.Card;
import it.unibo.cluedolite.model.player.api.Player;

/**
 * Responsible for distributing cards to players at the beginning of the game.
 * Cards are dealt one at a time in a round-robin fashion until all cards are distributed.
 */
public final class CardDistribution {

        /**
        * Distributes the given cards among the players in a round-robin fashion.
        *
        * @param cards   the list of {@link Card} objects to be distributed
        * @param players the list of {@link Player} objects that will receive the cards
        */
        public CardDistribution(List<Card> cards, List<Player> players) {
            IntStream.range(0, cards.size())
                .forEach(i -> players.get(i % players.size()).addCard(cards.get(i)));
        }
}
