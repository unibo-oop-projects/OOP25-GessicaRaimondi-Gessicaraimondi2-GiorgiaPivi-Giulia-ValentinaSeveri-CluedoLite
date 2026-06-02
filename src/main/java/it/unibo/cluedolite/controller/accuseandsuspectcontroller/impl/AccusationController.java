package it.unibo.cluedolite.controller.accuseandsuspectcontroller.impl;

import java.util.function.Consumer;

import javax.swing.JFrame;

import it.unibo.cluedolite.controller.accuseandsuspectcontroller.api.InterfaceAccusation;
import it.unibo.cluedolite.model.accuseandsuspect.api.InterfaceSuspicion;
import it.unibo.cluedolite.model.accuseandsuspect.api.InterfaceAccuseManager;
import it.unibo.cluedolite.model.accuseandsuspect.impl.Suspicion;
import it.unibo.cluedolite.model.creationcards.impl.Card;
import it.unibo.cluedolite.view.accuseview.AccuseView;

/**
 * Controller for the accusation phase of the CluedoLite game.
 * Acts as the bridge between {@link AccuseView} and {@link AccuseManager},
 * following the MVC pattern. Opens the accusation view lazily, reads the player's
 * selections, checks them against the secret solution, and delivers the result
 * via a callback. A second callback notifies the game when the accusation is confirmed.
 */
public class AccusationController implements InterfaceAccusation {

    private final InterfaceAccuseManager accuseManager;
    private final Consumer<Boolean> accusationResultCallback;
    private final Runnable onConfirmed;
    private final Card[] characters;
    private final Card[] weapons;
    private final Card[] rooms;

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
            final InterfaceAccuseManager accuseManager,
            final Card[] characters,
            final Card[] weapons,
            final Card[] rooms,
            final Consumer<Boolean> accusationResultCallback,
            final Runnable onConfirmed) {
        this.accuseManager = accuseManager;
        this.accusationResultCallback = accusationResultCallback;
        this.onConfirmed = onConfirmed;
        this.characters = characters;
        this.weapons = weapons;
        this.rooms = rooms;
    }

    /**
     * Opens the accusation view.
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
     * Attaches the confirm button listener to the given view instance.
     * The view is passed explicitly so there is no shared mutable state between calls.
     *
     * @param view the {@link AccuseView} instance to attach the listener to
     */
    private void setupListeners(final AccuseView view) {
        view.getConfirmButton().addActionListener(e -> handleConfirm(view));
    }

    /**
    * Handles the confirmation of the accusation.
    * Disables the confirm button, reads the player's selections,
    * checks them against the secret solution and delivers the result via callback.
    *
    * @param view the {@link AccuseView} instance that triggered the confirmation
    */
    private void handleConfirm(final AccuseView view) {
        view.getConfirmButton().setEnabled(false);
        onConfirmed.run();

        final Card selectedCharacter = view.getSelectedCharacter();
        final Card selectedWeapon    = view.getSelectedWeapon();
        final Card selectedRoom      = view.getSelectedRoom();

        final InterfaceSuspicion suspicion = new Suspicion(selectedCharacter, selectedWeapon, selectedRoom);
        final boolean result = accuseManager.checkAccuse(suspicion);

        accusationResultCallback.accept(result);
        view.dispose();
    }
}