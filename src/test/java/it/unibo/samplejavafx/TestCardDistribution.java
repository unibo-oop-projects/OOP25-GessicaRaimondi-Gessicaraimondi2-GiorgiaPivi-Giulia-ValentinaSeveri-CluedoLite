package it.unibo.samplejavafx;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

import it.unibo.cluedolite.model.creationcards.impl.Card;
import it.unibo.cluedolite.model.gamesetup.impl.CardDistribution;
import it.unibo.cluedolite.model.gamesetup.impl.Deck;
import it.unibo.cluedolite.model.gamesetup.impl.SecretSolution;
import it.unibo.cluedolite.model.player.api.Player;
import it.unibo.cluedolite.model.player.impl.PlayerImpl;

/**
 * Tests card distribution across different player counts (3, 4, 5, 6 players).
 */
public class TestCardDistribution {

    private static final int TOTAL_CARDS = 18;
    private static final int THREE_PLAYERS = 3;
    private static final int FOUR_PLAYERS = 4;
    private static final int FIVE_PLAYERS = 5;
    private static final int SIX_PLAYERS = 6;
    private static final int CARDS_TWO = 2;
    private static final int CARDS_THREE = 3;
    private static final int CARDS_FOUR = 4;
    private static final int CARDS_FIVE = 5;

    /**
     * Tests that cards are correctly distributed among players.
     */
    @Test
    void testCardDistributionEvenly() {
        final List<Card> cards = Deck.getAllCards();
        new SecretSolution(cards);

        final int totalCards = cards.size();
        assertEquals(TOTAL_CARDS, totalCards,
            "The deck should have 18 cards after removing the secret solution cards");

        final List<Player> players = Arrays.asList(
            new PlayerImpl("Alice"),
            new PlayerImpl("Bob"),
            new PlayerImpl("Carol"),
            new PlayerImpl("David"),
            new PlayerImpl("Eve"),
            new PlayerImpl("Frank")
        );

        new CardDistribution(cards, players);

        if (players.size() == THREE_PLAYERS) {
            final int expectedCardsPerPlayer = totalCards / THREE_PLAYERS;
            for (final Player player : players) {
                assertEquals(expectedCardsPerPlayer, player.getHand().size(),
                    "Each player should receive the correct number of cards");
            }
        } else if (players.size() == FOUR_PLAYERS) {
            int count4 = 0;
            int count5 = 0;
            for (final Player p : players) {
                final int handSize = p.getHand().size();
                if (handSize == CARDS_FOUR) {
                    count4++;
                } else if (handSize == CARDS_FIVE) {
                    count5++;
                }
            }
            assertTrue(count4 == CARDS_TWO && count5 == CARDS_TWO,
                "With 4 players, two should receive 4 cards and two should receive 5 cards");
        } else if (players.size() == FIVE_PLAYERS) {
            int count3 = 0;
            int count4 = 0;
            for (final Player p : players) {
                final int handSize = p.getHand().size();
                if (handSize == CARDS_THREE) {
                    count3++;
                } else if (handSize == CARDS_FOUR) {
                    count4++;
                }
            }
            assertTrue(count3 == CARDS_TWO && count4 == CARDS_THREE,
                "With 5 players, two should receive 3 cards and three should receive 4 cards");
        } else if (players.size() == SIX_PLAYERS) {
            final int expectedCardsPerPlayer = totalCards / SIX_PLAYERS;
            for (final Player player : players) {
                assertEquals(expectedCardsPerPlayer, player.getHand().size(),
                    "Each player should receive the correct number of cards");
            }
        }
    }
}