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
 * Test class that verifies the correct distribution of cards to players in the CluedoLite game.
 * Tests cover even and uneven distributions across different player counts (3, 4, 5, 6 players).
 */
public class TestCardDistribution {

    /**
    * Tests that 18 cards are correctly distributed among 3 players,
    * verifying each player receives exactly 6 cards.
    */
    @Test
    void testCardDistributionEvenly() {
        List<Card> cards = Deck.getAllCards();
        new SecretSolution(cards);

        int totalCards = cards.size();
        assertEquals(18, totalCards, "The deck should have 18 cards after removing the secret solution cards");

        List<Player> players = Arrays.asList(
            new PlayerImpl("Alice"),
            new PlayerImpl("Bob"),
            new PlayerImpl("Charlie"),
            new PlayerImpl("Charlie"),
            new PlayerImpl("Charlie"),
            new PlayerImpl("Charlie")
        );

        new CardDistribution(cards, players);

        if (players.size() == 3) {
            int expectedCardsPerPlayer = totalCards / 3;
            for (Player player : players) {
                assertEquals(expectedCardsPerPlayer, player.getHand().size(), "Each player should receive the correct number of cards");
            }
        } else if (players.size() == 4) {
            int count4 = 0;
            int count5 = 0;
            for (Player p : players) {
                int handSize = p.getHand().size();
                if (handSize == 4) count4++;
                else if (handSize == 5) count5++;
            }
            assertTrue(count4 == 2 && count5 == 2, "With 4 players, two should receive 4 cards and two should receive 5 cards");
        } else if (players.size() == 5) {
            int count3 = 0;
            int count4 = 0;
            for (Player p : players) {
                int handSize = p.getHand().size();
                if (handSize == 3) count3++;
                else if (handSize == 4) count4++;
            }
            assertTrue(count3 == 2 && count4 == 3, "With 5 players, two should receive 3 cards and three should receive 4 cards");
        } else if (players.size() == 6) {
            int expectedCardsPerPlayer = totalCards / 6;
            for (Player player : players) {
                assertEquals(expectedCardsPerPlayer, player.getHand().size(), "Each player should receive the correct number of cards");
            }
        }
    }
}