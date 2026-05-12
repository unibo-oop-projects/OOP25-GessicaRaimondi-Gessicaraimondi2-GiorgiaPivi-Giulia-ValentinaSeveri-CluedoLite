package it.unibo.CluedoLite.controller.accuseandsuspectcontroller.impl;

import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.swing.JOptionPane;

import it.unibo.CluedoLite.model.accuseandsuspect.impl.*;
import it.unibo.CluedoLite.controller.accuseandsuspectcontroller.api.*;
import it.unibo.CluedoLite.model.creationcards.impl.Card;
import it.unibo.CluedoLite.model.player.api.Player;
import it.unibo.CluedoLite.view.suspicionview.SuspicionView;

/**
 * Controller for the suspicion phase of the CluedoLite game.
 * This class acts as the bridge between the VIEW ({@link SuspicionView})
 * and the MODEL ({@link SuspicionManager}), following the MVC pattern.
 *
 * Responsibilities:
 *  - stores the data needed to open the suspicion view (characters, weapons, room)
 *  - creates the {@link SuspicionView} only when the player clicks the suspicion button
 *  - attaches the confirm button listener to the view
 *  - reads the player's choices from the view and passes them to the model
 *  - delivers the resulting {@link Suspicion} to the rest of the game via a callback
 *
 * The use of a {@link Consumer} callback keeps this controller fully decoupled
 * from the rest of the game flow: it does not know who will use the suspicion,
 * it simply delivers it when confirmed.
 */
public class SuspicionController implements InterfaceSuspicionController {

    private final SuspicionManager suspicionManager;
    private final Consumer<Suspicion> suspicionCallback;
    private final Card[] characters;
    private final Card[] weapons;
    private final Supplier<Card> roomSupplier;
    private final Supplier<Player> playerSupplier;

    /**
     * Constructs a {@link SuspicionController} with all the data needed for the suspicion phase.
     * The view is NOT created here: it is created lazily when {@link #openSuspicionView()} is called,
     * so that the window only appears when the player actually clicks the suspicion button.
     *
     * @param suspicionManager  the model component that creates {@link Suspicion} objects
     * @param player            the player who is making the suspicion
     * @param characters        array of available character cards to display in the view
     * @param weapons           array of available weapon cards to display in the view
     * @param room              the card representing the room where the player currently is
     * @param suspicionCallback callback invoked with the created {@link Suspicion} when confirmed
     */
    public SuspicionController(
            SuspicionManager suspicionManager,
            Card[] characters,
            Card[] weapons,
            Supplier<Card> roomSupplier,
            Consumer<Suspicion> suspicionCallback,
            Supplier<Player> playerSupplier
    ) {
        this.suspicionManager = suspicionManager;
        this.suspicionCallback = suspicionCallback;
        this.characters = characters;
        this.weapons = weapons;
        this.roomSupplier = roomSupplier;
        this.playerSupplier = playerSupplier;
    }

    /**
     * Creates and displays the {@link SuspicionView}.
     * Called externally by the suspicion button in the game screen.
     * The view is a local variable: each call creates a fully independent instance,
     * avoiding stale references if the window is opened more than once.
     */
    @Override
    public void openSuspicionView() {
        SuspicionView view = new SuspicionView(characters, weapons, roomSupplier.get());
        setupListeners(view);
        view.setVisible(true);
    }

    /**
     * Attaches the action listener to the confirm button of the given view instance.
     * The view is passed explicitly so there is no shared mutable state between calls.
     *
     * @param view the {@link SuspicionView} instance to attach the listener to
     */
    private void setupListeners(SuspicionView view) {
        view.getConfirmButton().addActionListener(e -> handleConfirm(view));
    }

    /**
     * Handles the confirmation of the suspicion.
     * This method is triggered when the player clicks the confirm button in the view.
     *
     * Flow:
     *  1. disables the confirm button immediately to prevent double-clicks
     *  2. reads the selected character and weapon from the view
     *  3. passes them together with the current room to the model
     *  4. if the model returns null (player not in a room), re-enables the button and shows an error
     *  5. otherwise, delivers the {@link Suspicion} to the game via the callback and closes the view
     *
     * @param view the {@link SuspicionView} instance that triggered the confirmation
     */
    private void handleConfirm(SuspicionView view) {

        // Point 5: disable the button immediately to prevent double-clicks
        view.getConfirmButton().setEnabled(false);

        Card selectedCharacter = view.getSelectedCharacter();
        Card selectedWeapon    = view.getSelectedWeapon();

        Suspicion suspicion = suspicionManager.makeSuspicion(playerSupplier.get(), selectedCharacter, selectedWeapon, roomSupplier.get());

        // If the suspicion is null, the player is not in a room: show an error and re-enable the button
        if (suspicion == null) {
            JOptionPane.showMessageDialog(view,
                    "You cannot make a suspicion because you are not in a room.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            // Re-enable the button so the player can try again
            view.getConfirmButton().setEnabled(true);
            return;
        }

        suspicionCallback.accept(suspicion);
        view.dispose();
    }
}