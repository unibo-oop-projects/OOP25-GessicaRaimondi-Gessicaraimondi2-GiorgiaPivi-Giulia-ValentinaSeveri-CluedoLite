package it.unibo.cluedolite.controller.accuseandsuspectcontroller.impl;

import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.swing.JOptionPane;

import it.unibo.cluedolite.controller.accuseandsuspectcontroller.api.InterfaceSuspicionController;
import it.unibo.cluedolite.model.accuseandsuspect.api.InterfaceSuspicion;
import it.unibo.cluedolite.model.accuseandsuspect.impl.Suspicion;
import it.unibo.cluedolite.model.accuseandsuspect.impl.SuspicionManager;
import it.unibo.cluedolite.model.creationcards.impl.Card;
import it.unibo.cluedolite.model.player.api.Player;
import it.unibo.cluedolite.view.suspicionview.SuspicionView;

/**
 * Controller for the suspicion phase of the CluedoLite game.
 * Acts as the bridge between {@link SuspicionView} and {@link SuspicionManager},
 * following the MVC pattern. Opens the suspicion view lazily, reads the player's
 * selections, passes them to the model and delivers the result via a callback.
 * A second callback notifies the game when the suspicion is confirmed.
 */
public class SuspicionController implements InterfaceSuspicionController {

    private final SuspicionManager suspicionManager;
    private final Consumer<InterfaceSuspicion> suspicionCallback;
    private final Runnable onConfirmed;
    private final Card[] characters;
    private final Card[] weapons;
    private final Supplier<Card> roomSupplier;
    private final Supplier<Player> playerSupplier;

    /**
     * Constructs a {@link SuspicionController} with all the data needed for the suspicion phase.
     * The view is NOT created here: it is created lazily when {@link #openSuspicionView()}
     * is called, so the window appears only when the player clicks the suspicion button.
     *
     * @param suspicionManager  the model component that creates {@link Suspicion} objects
     * @param characters        array of available character cards shown in the view
     * @param weapons           array of available weapon cards shown in the view
     * @param roomSupplier      supplier returning the card for the player's current room
     * @param suspicionCallback callback invoked with the created {@link Suspicion} on confirm
     * @param playerSupplier    supplier returning the current {@link Player}
     * @param onConfirmed       callback invoked immediately when the suspicion is confirmed,
     *                          used to disable action buttons in the game view
     */
    public SuspicionController(
            final SuspicionManager suspicionManager,
            final Card[] characters,
            final Card[] weapons,
            final Supplier<Card> roomSupplier,
            final Consumer<InterfaceSuspicion> suspicionCallback,
            final Supplier<Player> playerSupplier,
            final Runnable onConfirmed) {
        this.suspicionManager = suspicionManager;
        this.suspicionCallback = suspicionCallback;
        this.onConfirmed = onConfirmed;
        this.characters = characters;
        this.weapons = weapons;
        this.roomSupplier = roomSupplier;
        this.playerSupplier = playerSupplier;
    }

    /**
     * Creates and displays the {@link SuspicionView}.
     * Called externally by the suspicion button in the game screen.
     * Each call creates a fully independent view instance, avoiding stale references
     * if the window is opened more than once per session.
     */
    @Override
    public void openSuspicionView() {
        if (roomSupplier.get() == null) {
            JOptionPane.showMessageDialog(
                    null,
                    "You cannot make a suspicion because you are not in a room.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        onConfirmed.run();
        final SuspicionView view = new SuspicionView(characters, weapons, roomSupplier.get());
        setupListeners(view);
        view.setVisible(true);
    }

    /**
     * Attaches the action listener to the confirm button of the given view instance.
     * The view is passed explicitly so there is no shared mutable state between calls.
     *
     * @param view the {@link SuspicionView} instance to attach the listener to
     */
    private void setupListeners(final SuspicionView view) {
        view.getConfirmButton().addActionListener(e -> handleConfirm(view));
    }

    /**
     * Handles the confirmation of the suspicion.
     * Disables the confirm button, reads the player's selections,
     * passes them to the model and delivers the result via callback.
     *
     * @param view the {@link SuspicionView} instance that triggered the confirmation
     */
    private void handleConfirm(final SuspicionView view) {
        view.getConfirmButton().setEnabled(false);

        final Card selectedCharacter = view.getSelectedCharacter();
        final Card selectedWeapon    = view.getSelectedWeapon();

        final InterfaceSuspicion suspicion = suspicionManager.makeSuspicion(
                playerSupplier.get(), selectedCharacter, selectedWeapon, roomSupplier.get());

        if (suspicion == null) {
            JOptionPane.showMessageDialog(
                    view,
                    "You cannot make a suspicion because you are not in a room.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            view.getConfirmButton().setEnabled(true);
            return;
        }
        
        onConfirmed.run();
        suspicionCallback.accept(suspicion);
        view.dispose();
    }
}