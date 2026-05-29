package it.unibo.cluedolite.view.gameboardview.api;

public interface BoardView {
    /**
     * Requests a repaint of the board panel to reflect the current game state.
     * Called by the controller after a successful move or turn change.
     */
    void repaint();

    /**
     * Shows a warning message to the user when they attempt
     * to move to a non-adjacent room.
     */
    void wrongRoomSelected();
}
