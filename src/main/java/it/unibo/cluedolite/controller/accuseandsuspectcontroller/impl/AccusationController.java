package it.unibo.cluedolite.controller.accuseandsuspectcontroller.impl;

import java.util.Arrays;
import java.util.function.Consumer;

import javax.swing.JFrame;

import it.unibo.cluedolite.controller.accuseandsuspectcontroller.api.InterfaceAccusation;
import it.unibo.cluedolite.model.accuseandsuspect.impl.AccuseManager;
import it.unibo.cluedolite.model.accuseandsuspect.impl.Suspicion;
import it.unibo.cluedolite.model.creationcards.impl.AbstractCard;
import it.unibo.cluedolite.view.accuseview.AccuseView;

/**
 * Controller for the accusation phase of the CluedoLite game.
 *
 * <p>Acts as the bridge between {@link AccuseView} and {@link AccuseManager},
 * following the MVC pattern. Responsibilities:
 * <ul>
 *   <li>Stores the data needed to open the accusation view.</li>
 *   <li>Creates {@link AccuseView} lazily when the player clicks the accusation button.</li>
 *   <li>Attaches the confirm listener to the view.</li>
 *   <li>Reads the player's selections, checks them against the secret solution,
 *       and delivers the boolean result via a callback.</li>
 *   <li>Notifies the game that an action has been confirmed via a second callback,
 *       so that the action buttons can be disabled immediately.</li>
 * </ul>
 *
 * <p>The use of {@link Consumer} and {@link Runnable} callbacks keeps this controller
 * fully decoupled from the rest of the game flow.
 */
public class AccusationController implements InterfaceAccusation {

    private final AccuseManager accuseManager;
    private final Consumer<Boolean> accusationResultCallback;
    private final Runnable onConfirmed;
    private final AbstractCard[] characters;
    private final AbstractCard[] weapons;
    private final AbstractCard[] rooms;

    /**
     * Constructs an {@link AccusationController} with all the data needed
     * for the accusation phase.
     *
     * @param accuseManager             the model component that checks accusations
     * @param characters                array of available character cards shown in the view
     * @param weapons                   array of available weapon cards shown in the view
     * @param rooms                     array of available room cards shown in the view
     * @param accusationResultCallback  callback invoked with {@code true} if the accusation
     *                                  is correct, {@code false} otherwise
     * @param onConfirmed               callback invoked immediately when the accusation is
     *                                  confirmed, used to disable action buttons in the game view
     */
    public AccusationController(
            final AccuseManager accuseManager,
            final AbstractCard[] characters,
            final AbstractCard[] weapons,
            final AbstractCard[] rooms,
            final Consumer<Boolean> accusationResultCallback,
            final Runnable onConfirmed) {
        this.accuseManager = accuseManager;
        this.accusationResultCallback = accusationResultCallback;
        this.onConfirmed = onConfirmed;

        this.characters = Arrays.copyOf(characters, characters.length);
        this.weapons = Arrays.copyOf(weapons, weapons.length);
        this.rooms = Arrays.copyOf(rooms, rooms.length);
    }

    /**
     * Opens the accusation view in normal mode (closeable).
     * Each call creates a fresh {@link AccuseView} instance, avoiding stale references
     * if the window is opened more than once per session.
     */
    @Override
    public void openAccusationView() {
        final AccuseView view = new AccuseView(characters, weapons, rooms);
        view.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setupListeners(view);
        view.setVisible(true);
    }

    /**
     * Opens the accusation view in forced mode (not closeable).
     * Used when the last remaining player must submit a final accusation
     * before the game can end.
     */
    public void openForcedAccusationView() {
        final AccuseView view = new AccuseView(characters, weapons, rooms);
        view.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setupListeners(view);
        view.setVisible(true);
    }

    /**
     * Attaches the confirm button listener to the given view instance.
     * The view is passed explicitly so there is no shared mutable state between calls.
     *
     * @param view the {@link AccuseView} instance to attach the listener to
     */
    private void setupListeners(final AccuseView view) {
        view.addConfirmListener(e -> handleConfirm(view));
    }

    /**
     * Handles the confirmation of the accusation.
     *
     * <p>Flow:
     * <ol>
     *   <li>Disables the confirm button immediately to prevent double-clicks.</li>
     *   <li>Notifies the game view via {@code onConfirmed} to disable action buttons.</li>
     *   <li>Reads the player's selections from the view.</li>
     *   <li>Builds a {@link Suspicion} and checks it against the secret solution.</li>
     *   <li>Passes the result to the main controller via the callback.</li>
     *   <li>Closes the accusation window.</li>
     * </ol>
     *
     * @param view the {@link AccuseView} instance that triggered the confirmation
     */
    private void handleConfirm(final AccuseView view) {
        view.setConfirmEnabled(false);
        onConfirmed.run();

        final AbstractCard selectedCharacter = view.getSelectedCharacter();
        final AbstractCard selectedWeapon = view.getSelectedWeapon();
        final AbstractCard selectedRoom = view.getSelectedRoom();

        final Suspicion suspicion = new Suspicion(selectedCharacter, selectedWeapon, selectedRoom);
        final boolean result = accuseManager.checkAccuse(suspicion);

        accusationResultCallback.accept(result);
        view.dispose();
    }
}
