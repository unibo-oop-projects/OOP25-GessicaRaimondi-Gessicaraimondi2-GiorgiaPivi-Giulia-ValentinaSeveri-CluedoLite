package it.unibo.cluedolite.controller.accuseandsuspectcontroller.api;

/**
 * Defines the behavior of the controller responsible for handling
 * the accusation phase in the CluedoLite game.
 * 
 * <p>Implementations of this interface must manage the opening of the
 * accusation view and coordinate the interaction between the UI and
 * the underlying game logic.
 */
@FunctionalInterface
public interface InterfaceAccusation {

    /**
     * Opens the accusation view and prepares the UI for user interaction.
     */
    void openAccusationView();
}
