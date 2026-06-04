package it.unibo.samplejavafx;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.cluedolite.model.gameflow.api.GameState;
import it.unibo.cluedolite.model.gameflow.impl.GameImpl;
import it.unibo.cluedolite.model.player.impl.CreationCharacterImpl;
import it.unibo.cluedolite.model.player.impl.PlayerImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit tests for the GameFlow logic.
 */
class GameFlowTest {

    private static final int TOTAL_CHARACTERS = 6;

    private GameImpl game;
    private PlayerImpl p1;
    private PlayerImpl p2;
    private PlayerImpl p3;

    /**
     * Initializes the game and players before each test.
     */
    @BeforeEach
    void setUp() {
        game = new GameImpl(3);
        p1 = new PlayerImpl("Anna");
        p2 = new PlayerImpl("Chiara");
        p3 = new PlayerImpl("Sara");
    }

    /**
     * Tests that the game starts in MENU state.
     */
    @Test
    void testInitialStateIsMenu() {
        assertEquals(GameState.MENU, game.getState());
    }

    /**
     * Tests that enterLobby() transitions from MENU to WAITING.
     */
    @Test
    void testEnterLobby() {
        game.enterLobby();
        assertEquals(GameState.WAITING, game.getState());
    }

    /**
     * Tests that enterLobby() throws if not in MENU state.
     */
    @Test
    void testEnterLobbynotFromMenuthrows() {
        game.enterLobby(); 
        assertThrows(IllegalStateException.class, game::enterLobby);
    }

    /**
     * Tests that startGame() transitions from WAITING to IN_PROGRESS.
     */
    @Test
    void testStartGame() {
        game.enterLobby();
        game.setPlayer(0, p1);
        game.setPlayer(1, p2);
        game.setPlayer(2, p3);

        final List<CreationCharacterImpl> chars = new ArrayList<>(game.getAvailableCharacters());
        game.assignCharacterToPlayer(0, chars.get(0));
        game.assignCharacterToPlayer(1, chars.get(1));
        game.assignCharacterToPlayer(2, chars.get(2));

        game.startGame();
        assertEquals(GameState.IN_PROGRESS, game.getState());
    }

    /**
     * Tests that startGame() throws if not all characters are assigned.
     */
    @Test
    void testStartGamenotAllAssignedthrows() {
        game.enterLobby();
        game.setPlayer(0, p1); 
        assertThrows(IllegalStateException.class, game::startGame);
    }

    /**
     * Tests that startGame() throws if not in WAITING state.
     */
    @Test
    void testStartGamenotFromWaitingthrows() {
        assertThrows(IllegalStateException.class, game::startGame);
    }

    /**
     * Tests that quitToMenu() resets everything and goes back to MENU.
     */
    @Test
    void testQuitToMenufromWaiting() {
        game.enterLobby();
        game.setPlayer(0, p1);
        game.quitToMenu();

        assertEquals(GameState.MENU, game.getState());
        assertNull(game.getPlayers().get(0));
        assertEquals(TOTAL_CHARACTERS, game.getAvailableCharacters().size());
    }

    /**
     * Tests that quitToMenu() works from IN_PROGRESS.
     */
    @Test
    void testQuitToMenufromInProgress() {
        game.enterLobby();
        game.setPlayer(0, p1);
        game.setPlayer(1, p2);
        game.setPlayer(2, p3);

        final List<CreationCharacterImpl> chars = new ArrayList<>(game.getAvailableCharacters());
        game.assignCharacterToPlayer(0, chars.get(0));
        game.assignCharacterToPlayer(1, chars.get(1));
        game.assignCharacterToPlayer(2, chars.get(2));
        game.startGame();

        game.quitToMenu();
        assertEquals(GameState.MENU, game.getState());
        assertEquals(TOTAL_CHARACTERS, game.getAvailableCharacters().size());
    }

    /**
     * Tests that resetGame() keeps players but clears characters and back to WAITING.
     */
    @Test
    void testResetGame() {
        game.enterLobby();
        game.setPlayer(0, p1);
        game.setPlayer(1, p2);
        game.setPlayer(2, p3);

        final List<CreationCharacterImpl> chars = new ArrayList<>(game.getAvailableCharacters());
        game.assignCharacterToPlayer(0, chars.get(0));
        game.assignCharacterToPlayer(1, chars.get(1));
        game.assignCharacterToPlayer(2, chars.get(2));
        game.startGame();

        game.resetGame();

        assertEquals(GameState.WAITING, game.getState());
        assertEquals("Anna", game.getPlayers().get(0).getName()); 
        assertNull(game.getPlayers().get(0).getCharacter());
        assertEquals(TOTAL_CHARACTERS, game.getAvailableCharacters().size());
    }

    /**
     * Test if game is not IN_PROGRESS.
     */
    @Test
    void testResetGamenotInProgressthrows() {
        assertThrows(IllegalStateException.class, game::resetGame);
    }
}
