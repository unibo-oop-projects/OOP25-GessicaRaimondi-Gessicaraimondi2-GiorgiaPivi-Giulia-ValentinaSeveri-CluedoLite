package it.unibo.samplejavafx;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.unibo.cluedolite.model.gameboard.api.Room;
import it.unibo.cluedolite.model.gameboard.impl.GameBoardModelImpl;
import it.unibo.cluedolite.model.gameboard.impl.RoomImpl;
import it.unibo.cluedolite.model.player.impl.PlayerImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link GameBoardModelImpl}.
 */
final class GameBoardModelImplTest {

    private static final int EXPECTED_SIZE = 9;
    private GameBoardModelImpl board;
    private PlayerImpl player;

    @BeforeEach
    void setUp() {
        board = new GameBoardModelImpl();
        player = new PlayerImpl("Scarlett");
    }

    @Test
    void testGetRoomsReturnsAllNineRooms() {
        assertEquals(EXPECTED_SIZE, board.getRooms().size());
    }

    @Test
    void testGetRoomsReturnsUnmodifiableCopy() {
        assertThrows(UnsupportedOperationException.class, () -> board.getRooms().add(new RoomImpl("FakeRoom")));
    }

    @Test
    void testSetAndGetPlayerPosition() {
        final Room kitchen = board.getRooms().get(0);
        board.setPlayerPosition(player, kitchen);
        assertEquals(kitchen, board.getPlayerPosition(player));
    }

    @Test
    void testAreAdjacentTrue() {
        final Room r1 = board.getRooms().get(0);
        final Room r2 = board.getRooms().get(1);
        assertTrue(board.areAdjacent(r1, r2));
    }

    @Test
    void testAreAdjacentFalse() {
        final Room r1 = board.getRooms().get(0);
        final Room r3 = board.getRooms().get(3);
        assertFalse(board.areAdjacent(r1, r3));
    }

    @Test
    void testCanMoveToAnyRoomAtStart() {
        final Room anyRoom = board.getRooms().get(5);
        assertTrue(board.canMoveTo(player, anyRoom));
    }

    @Test
    void testCanMoveToAdjacentRoom() {
        final Room kitchen = board.getRooms().get(0);
        final Room ballroom = board.getRooms().get(1);
        board.setPlayerPosition(player, kitchen);
        assertTrue(board.canMoveTo(player, ballroom));
    }

    @Test
    void testCannotMoveToNonAdjacentRoom() {
        final Room kitchen = board.getRooms().get(0);
        final Room library = board.getRooms().get(4);
        board.setPlayerPosition(player, kitchen);
        assertFalse(board.canMoveTo(player, library));
    }
}
