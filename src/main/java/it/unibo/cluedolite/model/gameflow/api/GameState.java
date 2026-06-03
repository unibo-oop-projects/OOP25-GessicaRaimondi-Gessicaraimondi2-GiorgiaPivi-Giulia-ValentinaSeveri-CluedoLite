package it.unibo.cluedolite.model.gameflow.api;

/**
 * Represents the possible states of a CluedoLite game.
 * The game transitions through these states during its lifecycle.
 */
public enum GameState {

    /** The game is at the main menu. */
    MENU,

    /** The game is in the lobby, waiting for players to select characters. */
    WAITING,

    /** The game is currently in progress. */
    IN_PROGRESS,

    /** The game has finished. */
    FINISHED
}
