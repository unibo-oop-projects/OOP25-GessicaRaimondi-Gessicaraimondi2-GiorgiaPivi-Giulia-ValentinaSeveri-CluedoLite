package it.unibo.samplejavafx;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import org.junit.jupiter.api.Test;

import it.unibo.cluedolite.model.creationcards.impl.Card;
import it.unibo.cluedolite.model.creationcards.impl.CardType;
import it.unibo.cluedolite.model.gamesetup.impl.Deck;
import it.unibo.cluedolite.model.gamesetup.impl.SecretSolution;

/**
 * Test class that verifies the correct generation of the secret solution in CluedoLite.
 * Checks that the solution contains exactly one card of each type and that
 * the selected cards are removed from the remaining deck.
 */
public class TestSecretSolution {

    /**
    * Tests that the secret solution is generated correctly,
    * containing exactly one character, one weapon, and one room card,
    * and that those cards are removed from the remaining deck.
    */
    @Test
    public void testSecretSolutionGeneration() {
        List<Card> cards = Deck.getAllCards();

        SecretSolution secretSolution = new SecretSolution(cards);
        List<Card> sol = secretSolution.getSolution();

        assertEquals(3, sol.size());

        boolean hasCharacter = sol.stream().anyMatch(c -> c.getType() == CardType.CHARACTER);
        boolean hasWeapon    = sol.stream().anyMatch(c -> c.getType() == CardType.WEAPON);
        boolean hasRoom      = sol.stream().anyMatch(c -> c.getType() == CardType.ROOM);

        assertTrue(hasCharacter);
        assertTrue(hasWeapon);
        assertTrue(hasRoom);

        for (Card c : sol) {
            assertFalse(cards.contains(c));
        }

        assertEquals(18, cards.size());
    }
}