package it.unibo.cluedolite.controller.accuseandsuspectcontroller.api;

/**
 * Interface for the suspicion controller in CluedoLite.
 * Defines the contract for opening the suspicion view,
 * used by external components such as {@link it.unibo.cluedolite.view.suspicionview.ButtonSuspicionView}.
 */
public interface InterfaceSuspicionController {

    /**
     * Opens the suspicion view.
     * Called externally by the suspicion button in the game screen.
     * Each call must create a fully independent view instance.
     */
    void openSuspicionView();
}