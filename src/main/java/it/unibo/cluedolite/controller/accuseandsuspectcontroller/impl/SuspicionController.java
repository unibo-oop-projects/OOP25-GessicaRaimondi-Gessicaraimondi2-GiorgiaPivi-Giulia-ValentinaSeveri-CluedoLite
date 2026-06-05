package it.unibo.cluedolite.controller.accuseandsuspectcontroller.impl;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.swing.JOptionPane;

import it.unibo.cluedolite.controller.accuseandsuspectcontroller.api.InterfaceSuspicionController;
import it.unibo.cluedolite.model.accuseandsuspect.api.InterfaceSuspicion;
import it.unibo.cluedolite.model.accuseandsuspect.impl.SuspicionManager;
import it.unibo.cluedolite.model.accuseandsuspect.impl.Suspicion;
import it.unibo.cluedolite.model.creationcards.impl.AbstractCard;
import it.unibo.cluedolite.model.player.api.Player;
import it.unibo.cluedolite.view.suspicionview.SuspicionView;

/**
 * Controller for the suspicion phase of the CluedoLite game.
 *
 * <p>Acts as the bridge between {@link SuspicionView} and {@link SuspicionManager},
 * following the MVC pattern. Responsibilities:
 * <ul>
 *   <li>Stores the data needed to open the suspicion view.</li>
 *   <li>Creates {@link SuspicionView} lazily when the player clicks the suspicion button.</li>
 *   <li>Attaches the confirm listener to the view.</li>
 *   <li>Reads the player's selections and passes them to the model.</li>
 *   <li>Delivers the resulting {@link Suspicion} via a callback.</li>
 *   <li>Notifies the game that an action has been confirmed via a second callback,
 *       so that the action buttons can be disabled immediately.</li>
 * </ul>
 *
 * <p>The use of {@link Consumer} and {@link Runnable} callbacks keeps this controller
 * fully decoupled from the rest of the game flow.
 */
public class SuspicionController implements InterfaceSuspicionController {

    private final SuspicionManager suspicionManager;
    private final Consumer<InterfaceSuspicion> suspicionCallback;
    private final Runnable onConfirmed;
    private final AbstractCard[] characters;
    private final AbstractCard[] weapons;
    private final Supplier<AbstractCard> roomSupplier;
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
            final AbstractCard[] characters,
            final AbstractCard[] weapons,
            final Supplier<AbstractCard> roomSupplier,
            final Consumer<InterfaceSuspicion> suspicionCallback,
            final Supplier<Player> playerSupplier,
            final Runnable onConfirmed) {
        this.suspicionManager = suspicionManager;
        this.suspicionCallback = suspicionCallback;
        this.onConfirmed = onConfirmed;
        this.characters = Arrays.copyOf(characters, characters.length);
        this.weapons = Arrays.copyOf(weapons, weapons.length);
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
         view.addConfirmListener(e -> handleConfirm(view));
    }

    /**
     * Handles the confirmation of the suspicion.
     *
     * <p>Flow:
     * <ol>
     *   <li>Disables the confirm button immediately to prevent double-clicks.</li>
     *   <li>Notifies the game view via {@code onConfirmed} to disable action buttons.</li>
     *   <li>Reads the selected character and weapon from the view.</li>
     *   <li>Passes them together with the current room to the model.</li>
     *   <li>If the model returns {@code null} (player not in a room), re-enables
     *       the button and shows an error dialog.</li>
     *   <li>Otherwise, delivers the {@link Suspicion} via the callback and closes the view.</li>
     * </ol>
     *
     * @param view the {@link SuspicionView} instance that triggered the confirmation
     */
    private void handleConfirm(final SuspicionView view) {
        view.setConfirmEnabled(false);

        final AbstractCard selectedCharacter = view.getSelectedCharacter();
        final AbstractCard selectedWeapon = view.getSelectedWeapon();

        final InterfaceSuspicion suspicion = suspicionManager.makeSuspicion(
                playerSupplier.get(), selectedCharacter, selectedWeapon, roomSupplier.get());
        onConfirmed.run();
        suspicionCallback.accept(suspicion);
        view.dispose();
    }
}
