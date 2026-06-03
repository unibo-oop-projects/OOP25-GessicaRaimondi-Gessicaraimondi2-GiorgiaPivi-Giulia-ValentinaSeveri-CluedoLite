package it.unibo.samplejavafx;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.cluedolite.controller.gameboardcontroller.api.GameBoardController;
import it.unibo.cluedolite.controller.gameboardcontroller.impl.GameBoardControllerImpl;
import it.unibo.cluedolite.model.gameboard.api.Room;
import it.unibo.cluedolite.model.gameboard.impl.GameBoardModelImpl;
import it.unibo.cluedolite.model.player.impl.PlayerImpl;
import it.unibo.cluedolite.model.turnmanager.impl.TurnManagerImpl;
import it.unibo.cluedolite.view.gameboardview.api.BoardView;

import java.util.List;
 
import static org.junit.jupiter.api.Assertions.*;

 
public class TestGameBoardController {
 
    // Stub della view: non usa Swing, registra solo le chiamate 
    private static class BoardStub implements BoardView {
        boolean repaintCalled = false;
        boolean wrongRoomCalled = false;
 
        @Override
        public void repaint() {
            repaintCalled = true;
        }
 
        public void reset() {
            repaintCalled = false;
            wrongRoomCalled = false;
        }
    }
 
    //  Fixtures
 
    private GameBoardModelImpl boardModel;
    private TurnManagerImpl turnManager;
    private BoardStub view;
    private GameBoardController controller;
    private PlayerImpl player1;
    private PlayerImpl player2;
 
    @BeforeEach
    void setUp() {
        player1 = new PlayerImpl("Alice");
        player2 = new PlayerImpl("Bob");
 
        boardModel = new GameBoardModelImpl();
        turnManager = new TurnManagerImpl(List.of(player1, player2));
        view = new BoardStub();
        controller = new GameBoardControllerImpl(boardModel, turnManager);
        controller.setView(view);
    }
 
    // Test move()
 
    @Test
    void testMoveToAnyRoomWhenNotPlaced() {
        // Il giocatore non è ancora posizionato → può andare ovunque
        Room kitchen = boardModel.getRoomByName("Kitchen");
 
        controller.move(kitchen);
 
        assertEquals(kitchen, boardModel.getPlayerPosition(player1));
        assertTrue(view.repaintCalled);
        assertFalse(view.wrongRoomCalled);
    }
 
    @Test
    void testMoveToAdjacentRoom() {
        // Posiziona player1 in Kitchen, poi si sposta in una stanza adiacente
        Room kitchen = boardModel.getRoomByName("Kitchen");
        Room adjacent = kitchen.getAdjacent().get(0);
        boardModel.setPlayerPosition(player1, kitchen);
        view.reset();
 
        controller.move(adjacent);
 
        assertEquals(adjacent, boardModel.getPlayerPosition(player1));
        assertTrue(view.repaintCalled);
        assertFalse(view.wrongRoomCalled);
    }
 
    @Test
    void testMoveToNonAdjacentRoomFails() {
        // Posiziona player1 in Kitchen, poi tenta di andare in una stanza non adiacente
        Room kitchen = boardModel.getRoomByName("Kitchen");
        boardModel.setPlayerPosition(player1, kitchen);
 
        // Trova una stanza non adiacente a Kitchen
        Room nonAdjacent = boardModel.getRooms().stream()
                .filter(r -> !kitchen.getAdjacent().contains(r) && !r.equals(kitchen))
                .findFirst()
                .orElseThrow();
        view.reset();
 
        controller.move(nonAdjacent);
 
        // La posizione non deve essere cambiata
        assertEquals(kitchen, boardModel.getPlayerPosition(player1));
        assertFalse(view.repaintCalled);
        assertTrue(view.wrongRoomCalled);
    }
 
    //Test currentPlayer() 

    @Test
    void testCurrentPlayerIsFirstPlayer() {
        assertEquals(player1, controller.currentPlayer());
    }
 
    // Test endTurn()
 
    @Test
    void testEndTurnAdvancesToNextPlayer() {
        controller.endTurn();
        assertEquals(player2, controller.currentPlayer());
    }
 
    @Test
    void testEndTurnWrapsAround() {
        controller.endTurn(); // → player2
        controller.endTurn(); // → player1
        assertEquals(player1, controller.currentPlayer());
    }
 
    @Test
    void testEndTurnCallsRepaint() {
        view.reset();
        controller.endTurn();
        assertTrue(view.repaintCalled);
    }
 
    @Test
    void testEndTurnSkipsEliminatedPlayer() {
        player2.eliminate();
        controller.endTurn(); // player2 è eliminato → deve tornare player1
        assertEquals(player1, controller.currentPlayer());
    }
 
    // Test getRoomByName()
 
    @Test
    void testGetRoomByNameReturnsCorrectRoom() {
        Room room = controller.getRoomByName("Kitchen");
        assertNotNull(room);
        assertEquals("Kitchen", room.getName());
    }
 
    @Test
    void testGetRoomByNameReturnsNullIfNotFound() {
        Room room = controller.getRoomByName("NonExistentRoom");
        assertNull(room);
    }
 
    // Test getCurrentRoomOf() 
 
    @Test
    void testGetCurrentRoomOfReturnsNullBeforePlacement() {
        assertNull(controller.getCurrentRoomOf(player1));
    }
 
    @Test
    void testGetCurrentRoomOfReturnsCorrectRoomAfterMove() {
        Room kitchen = boardModel.getRoomByName("Kitchen");
        controller.move(kitchen);
        assertEquals(kitchen, controller.getCurrentRoomOf(player1));
    }
}
 
