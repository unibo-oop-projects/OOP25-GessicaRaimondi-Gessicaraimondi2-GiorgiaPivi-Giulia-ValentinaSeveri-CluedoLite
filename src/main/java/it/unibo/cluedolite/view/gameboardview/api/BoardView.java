package it.unibo.cluedolite.view.gameboardview.api;

/**
 * Public interface for the game board view.
 * Allows the controller to request a repaint of the board.
 */
@FunctionalInterface
public interface BoardView {

    /**
     * Requests a repaint of the board panel to reflect the current game state.
     */
    void repaint();
}
