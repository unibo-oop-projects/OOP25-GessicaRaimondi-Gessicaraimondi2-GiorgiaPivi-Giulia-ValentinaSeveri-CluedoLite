package it.unibo.CluedoLite.controller.accuseandsuspectcontroller.api;

import it.unibo.CluedoLite.model.creationcards.impl.Card;
import it.unibo.CluedoLite.model.player.api.Player;

/**
 * Defines the contract for the controller that manages the suspicion phase.
 *
 * The controller that implements this interface is responsible for:
 *  - opening the suspicion view when the player requests it
 *
 * It does not expose handleConfirm or setupListeners because those are
 * internal implementation details — only openSuspicionView is part of
 * the public contract that other components (e.g. ButtonSuspicionView) depend on.
 */
public interface InterfaceSuspicionController {

    /**
     * Opens the suspicion view.
     * Called externally by the suspicion button in the game screen.
     * Each call must create a fully independent view instance.
     */
    void openSuspicionView();

}