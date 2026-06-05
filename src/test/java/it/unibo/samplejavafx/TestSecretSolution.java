package it.unibo.samplejavafx;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import org.junit.jupiter.api.Test;

import it.unibo.cluedolite.model.creationcards.impl.AbstractCard;
import it.unibo.cluedolite.model.creationcards.impl.CardType;
import it.unibo.cluedolite.model.gamesetup.impl.Deck;
import it.unibo.cluedolite.model.gamesetup.impl.SecretSolution;

/**
 * Test class that verifies the correct generation of the secret solution in CluedoLite.
 * Checks that the solution contains exactly one card of each type and that
 * the selected cards are removed from the remaining deck.
 */
class TestSecretSolution {

    private static final int SOLUTION_SIZE = 3;
    private static final int REMAINING_CARDS = 18;

    /**
     * Tests that the secret solution is generated correctly,
     * containing exactly one character, one weapon, and one room card,
     * and that those cards are removed from the remaining deck.
     */
    @Test
    void testSecretSolutionGeneration() {
        final List<AbstractCard> cards = Deck.getAllCards();

        final SecretSolution secretSolution = new SecretSolution(cards);
        final List<AbstractCard> sol = secretSolution.getSolution();

        assertEquals(SOLUTION_SIZE, sol.size());

        final boolean hasCharacter = sol.stream().anyMatch(c -> c.getType() == CardType.CHARACTER);
        final boolean hasWeapon = sol.stream().anyMatch(c -> c.getType() == CardType.WEAPON);
        final boolean hasRoom = sol.stream().anyMatch(c -> c.getType() == CardType.ROOM);

        assertTrue(hasCharacter);
        assertTrue(hasWeapon);
        assertTrue(hasRoom);

        for (final AbstractCard c : sol) {
            assertFalse(cards.contains(c));
        }

        assertEquals(REMAINING_CARDS, cards.size());
    }
}
