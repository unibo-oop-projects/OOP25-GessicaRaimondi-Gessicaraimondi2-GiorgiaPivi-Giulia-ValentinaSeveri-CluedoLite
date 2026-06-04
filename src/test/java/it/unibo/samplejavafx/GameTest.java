package it.unibo.samplejavafx;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import it.unibo.cluedolite.model.gameflow.impl.GameImpl;
import it.unibo.cluedolite.model.player.impl.CreationCharacterImpl;
import it.unibo.cluedolite.model.player.impl.PlayerImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit tests for the GameImpl class.
 */
class GameTest {

    private static final int TOTAL_CHARACTERS = 6;
    private static final int INVALID_PLAYER_COUNT = 7;
    private static final String PLAYER_NAME_ANNA = "Anna";
    private static final String PLAYER_NAME_CHIARA = "Chiara";
    private static final String PLAYER_NAME_SARA = "Sara";

    /**
     * Tests that the game is created correctly with a valid number of players.
     */
    @Test
    void testValidNumberOfPlayers() {
        assertDoesNotThrow(() -> new GameImpl(3));
    }

    /**
     * Tests that the game is created correctly with a valid number of players.
     */
    @Test
    void testInvalidNumberOfPlayers() {
        assertThrows(IllegalArgumentException.class, () -> new GameImpl(2));
        assertThrows(IllegalArgumentException.class, () -> new GameImpl(INVALID_PLAYER_COUNT));
    }

    /**
     * Tests that the available characters list contains exactly 6 characters.
     */
    @Test
    void testAvailableCharactersCount() {
        final GameImpl game = new GameImpl(3);
        assertEquals(TOTAL_CHARACTERS, game.getAvailableCharacters().size());
    }

    /**
     * Tests that a player can be correctly inserted into the game.
     */
    @Test
    void testSetPlayerCorrectly() {
        final GameImpl game = new GameImpl(3);
        final PlayerImpl p = new PlayerImpl(PLAYER_NAME_ANNA);
        game.setPlayer(0, p);
        assertEquals(p, game.getPlayers().get(0));
    }

    /**
     * Tests that setting a player out of bounds throws an exception.
     */
    @Test
    void testSetPlayerOutOfBounds() {
        final GameImpl game = new GameImpl(3);
        assertThrows(IndexOutOfBoundsException.class, () -> game.setPlayer(3, new PlayerImpl("Test")));
    }

    /**
     * Tests that assigning a character to a valid player works correctly.
     */
    @Test
    void testAssignCharacterCorrectly() {
        final GameImpl game = new GameImpl(3);

        final PlayerImpl p1 = new PlayerImpl(PLAYER_NAME_ANNA);
        game.setPlayer(0, p1);

        final CreationCharacterImpl c = game.getAvailableCharacters().get(0);
        game.assignCharacterToPlayer(0, c);

        assertEquals(c, p1.getCharacter());
    }

    /**
     * Tests that assigning a character to a null player throws an exception.
     */
    @Test
    void testAssignCharacterToNullPlayer() {
        final GameImpl game = new GameImpl(3);
        final CreationCharacterImpl c = game.getAvailableCharacters().get(0);

        assertThrows(IllegalStateException.class, () -> game.assignCharacterToPlayer(0, c));
    }

    /**
     * Tests that a character cannot be assigned twice.
     */
    @Test
    void testAssignDuplicateCharacter() {
        final GameImpl game = new GameImpl(3);

        final PlayerImpl p1 = new PlayerImpl(PLAYER_NAME_ANNA);
        final PlayerImpl p2 = new PlayerImpl(PLAYER_NAME_CHIARA);

        game.setPlayer(0, p1);
        game.setPlayer(1, p2);

        final CreationCharacterImpl c = game.getAvailableCharacters().get(0);

        game.assignCharacterToPlayer(0, c);

        assertThrows(IllegalArgumentException.class, () -> game.assignCharacterToPlayer(1, c));
    }

    /**
     * Tests that an assigned character is removed from the list of available characters.
     */
    @Test
    void testCharacterIsRemovedFromAvailableList() {
        final GameImpl game = new GameImpl(3);

        final PlayerImpl p1 = new PlayerImpl(PLAYER_NAME_ANNA);
        game.setPlayer(0, p1);

        final CreationCharacterImpl c = game.getAvailableCharacters().get(0);

        game.assignCharacterToPlayer(0, c);

        assertFalse(game.getAvailableCharacters().contains(c));
    }

    /**
     * Tests that players are added, characters assigned, and duplicates prevented.
     */
    @Test
    void testGameSetupAndCharacterAssignment() {
        final GameImpl game = new GameImpl(3);

        final PlayerImpl p1 = new PlayerImpl(PLAYER_NAME_ANNA);
        final PlayerImpl p2 = new PlayerImpl(PLAYER_NAME_CHIARA);
        final PlayerImpl p3 = new PlayerImpl(PLAYER_NAME_SARA);

        game.setPlayer(0, p1);
        game.setPlayer(1, p2);
        game.setPlayer(2, p3);

        final List<CreationCharacterImpl> characters = new ArrayList<>(game.getAvailableCharacters());

        game.assignCharacterToPlayer(0, characters.get(0)); 
        game.assignCharacterToPlayer(1, characters.get(1)); 

        assertEquals("Miss Scarlet", p1.getCharacter().getName());
        assertEquals("RED", p1.getCharacter().getColor());

        assertEquals("Colonel Mustard", p2.getCharacter().getName());
        assertEquals("YELLOW", p2.getCharacter().getColor());

        assertThrows(IllegalArgumentException.class, () -> {
            game.assignCharacterToPlayer(2, characters.get(0));
        });

        game.assignCharacterToPlayer(2, characters.get(2));

        assertEquals("Mrs. White", p3.getCharacter().getName());
    }
}


