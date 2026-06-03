package it.unibo.samplejavafx;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.cluedolite.controller.gameboardcontroller.api.GameBoardController;
import it.unibo.cluedolite.controller.gameboardcontroller.impl.GameBoardControllerImpl;
import it.unibo.cluedolite.model.gameboard.api.GameBoardModel;
import it.unibo.cluedolite.model.gameboard.api.Room;
import it.unibo.cluedolite.model.gameboard.impl.GameBoardModelImpl;
import it.unibo.cluedolite.model.player.api.Player;
import it.unibo.cluedolite.model.player.impl.PlayerImpl;
import it.unibo.cluedolite.model.turnmanager.api.TurnManager;
import it.unibo.cluedolite.model.turnmanager.impl.TurnManagerImpl;
import it.unibo.cluedolite.view.gameboardview.api.BoardView;

final class TestGameBoardController {

    private static final String KITCHEN = "Kitchen";

    private GameBoardController controller;
    private GameBoardModel model;
    private TurnManager turnManager;

   @BeforeEach
    void setUp() {
        model = new GameBoardModelImpl();

        final Player p1 = new PlayerImpl("Alice");
        final Player p2 = new PlayerImpl("Bob");
        final Player p3 = new PlayerImpl("Carol");

        turnManager = new TurnManagerImpl(Arrays.asList(p1, p2, p3));
        controller = new GameBoardControllerImpl(model, turnManager);

        controller.setView(new DummyView());
    }


    @Test
    void testMoveToKitchen() {
        final Room kitchen = model.getRoomByName("kitchen");
        controller.move(kitchen);

        assertEquals(
            KITCHEN,
            model.getPlayerPosition(turnManager.getCurrentPlayer()).getName()
        );
    }

    private static final class DummyView implements BoardView {
        @Override
        public void repaint() { }
    }
}
