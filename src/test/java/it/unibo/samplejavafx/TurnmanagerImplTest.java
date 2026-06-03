package it.unibo.samplejavafx;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.unibo.cluedolite.model.accuseandsuspect.impl.Suspicion;
import it.unibo.cluedolite.model.creationcards.impl.AbstractCard;
import it.unibo.cluedolite.model.creationcards.impl.Characters;
import it.unibo.cluedolite.model.creationcards.impl.Rooms;
import it.unibo.cluedolite.model.creationcards.impl.Weapons;
import it.unibo.cluedolite.model.player.api.Player;
import it.unibo.cluedolite.model.player.impl.PlayerImpl;
import it.unibo.cluedolite.model.turnmanager.api.TurnManager;
import it.unibo.cluedolite.model.turnmanager.impl.TurnManagerImpl;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link TurnManagerImpl}.
 */
final class TurnmanagerImplTest {

    private PlayerImpl p1;
    private PlayerImpl p2;
    private PlayerImpl p3;
    private PlayerImpl p4;

    private List<Player> players;

    private AbstractCard character;
    private AbstractCard weapon;
    private AbstractCard room;

    private Suspicion suspect;

    /**
     * Il TurnManager usato nei test.
     */
    private TurnManager tm;

    /**
     * Initializes players and cards before each test.
     */
    @BeforeEach
    void init() {
        p1 = new PlayerImpl("Giulia");
        p2 = new PlayerImpl("Giorgia");
        p3 = new PlayerImpl("Gessica");
        p4 = new PlayerImpl("Valentina");

        players = List.of(p1, p2, p3, p4);

        character = new Characters("Miss Scarlett");
        weapon = new Weapons("Dagger");
        room = new Rooms("Kitchen");

        suspect = new Suspicion(character, weapon, room);

        tm = new TurnManagerImpl(players);
    }

    @Test
    void testTurn() {
        final TurnManager localTm = new TurnManagerImpl(players);
        assertEquals(p1, localTm.getCurrentPlayer());
        assertEquals(p2, localTm.nextTurn());
        assertEquals(p3, localTm.nextTurn());
        assertEquals(p4, localTm.nextTurn());
        assertEquals(p1, localTm.nextTurn());
    }

    @Test
    void testEndGame() {
        final TurnManager localTm = new TurnManagerImpl(players);
        localTm.endGame();
        assertTrue(localTm.isGameOver());
    }

    @Test
    void testNextTurnThrowsWhenGameOver() {
        final TurnManager localTm = new TurnManagerImpl(players);
        localTm.endGame();
        assertThrows(IllegalStateException.class, localTm::nextTurn);
    }

    @Test
    void testGetCurrentPlayerAfterGameOver() {
        final TurnManager localTm = new TurnManagerImpl(players);
        localTm.nextTurn();
        localTm.endGame();
        assertEquals(p2, localTm.getCurrentPlayer());
    }

    @Test
    void testSuggestionResponseFirstMatch() {
        p2.addCard(character);
        assertEquals(Optional.of(character), tm.checkSuspicion(suspect));
    }

    @Test
    void testSuggestionResponseSkipsNoMatch() {
        p3.addCard(weapon);
        assertEquals(Optional.of(weapon), tm.checkSuspicion(suspect));
    }

    @Test
    void testSuggestionResponseNoMatch() {
        assertTrue(tm.checkSuspicion(suspect).isEmpty());
    }

    @Test
    void testSuggestionResponseCircularOrder() {
        tm.nextTurn(); // currentPlayer = p2
        p1.addCard(room);
        assertEquals(Optional.of(room), tm.checkSuspicion(suspect));
    }
}
